package com.paranid5.cooking_corner.feature.main.profile.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.paranid5.cooking_corner.component.componentScope
import com.paranid5.cooking_corner.component.getComponentState
import com.paranid5.cooking_corner.component.toStateFlow
import com.paranid5.cooking_corner.core.common.AppDispatchers
import com.paranid5.cooking_corner.core.common.isForbidden
import com.paranid5.cooking_corner.domain.auth.AuthRepository
import com.paranid5.cooking_corner.domain.auth.getMe
import com.paranid5.cooking_corner.domain.global_event.GlobalEvent
import com.paranid5.cooking_corner.domain.global_event.GlobalEvent.LogOut.Reason
import com.paranid5.cooking_corner.domain.global_event.GlobalEventRepository
import com.paranid5.cooking_corner.domain.global_event.sendLogOut
import com.paranid5.cooking_corner.feature.main.profile.entity.ProfileUiState
import com.paranid5.cooking_corner.feature.main.profile.utils.fromResponse
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.toUiState
import com.paranid5.cooking_corner.utils.doNothing
import com.paranid5.cooking_corner.utils.updateState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable

internal class ProfileComponentImpl(
    componentContext: ComponentContext,
    private val authRepository: AuthRepository,
    private val globalEventRepository: GlobalEventRepository,
    private val onBack: () -> Unit,
) : ProfileComponent, ComponentContext by componentContext {
    @Serializable
    sealed interface Slot {
        @Serializable
        data object Edit : Slot
    }

    private val componentState = getComponentState(defaultState = ProfileState())

    private val _stateFlow = MutableStateFlow(componentState.value)
    override val stateFlow = _stateFlow.asStateFlow()

    private val childSlotNavigation = SlotNavigation<Slot>()

    override val childSlot: StateFlow<ChildSlot<*, ProfileChild>> = childSlot(
        source = childSlotNavigation,
        serializer = Slot.serializer(),
        childFactory = ::createChildSlot,
    ).toStateFlow()

    init {
        doOnCreate { componentScope.launch { loadProfile() } }
    }

    override fun onUiIntent(intent: ProfileUiIntent) {
        when (intent) {
            is ProfileUiIntent.Edit -> doNothing // TODO: Edit profile
            is ProfileUiIntent.LogOut -> componentScope.launch { logOut(Reason.MANUAL) }
        }
    }

    private fun createChildSlot(
        configuration: Slot,
        componentContext: ComponentContext,
    ) = when (configuration) {
        is Slot.Edit -> ProfileChild.Edit
    }

    private suspend fun loadProfile() {
        _stateFlow.updateState { copy(uiState = UiState.Loading) }

        val uiState = withContext(AppDispatchers.Data) {
            authRepository.getMe().fold(
                ifLeft = Throwable::toUiState,
                ifRight = { res ->
                    res.fold(
                        ifLeft = { status ->
                            if (status.isForbidden) logOut(Reason.ERROR)
                            null
                        },
                        ifRight = { ProfileUiState.fromResponse(it).toUiState() }
                    )
                }
            )
        } ?: return

        _stateFlow.updateState { copy(uiState = uiState) }
    }

    private suspend fun logOut(reason: Reason) =
        globalEventRepository.sendLogOut(reason)

    class Factory(
        private val authRepository: AuthRepository,
        private val globalEventRepository: GlobalEventRepository,
    ) : ProfileComponent.Factory {
        override fun create(
            componentContext: ComponentContext,
            onBack: () -> Unit,
        ): ProfileComponent = ProfileComponentImpl(
            componentContext = componentContext,
            authRepository = authRepository,
            globalEventRepository = globalEventRepository,
            onBack = onBack,
        )
    }
}