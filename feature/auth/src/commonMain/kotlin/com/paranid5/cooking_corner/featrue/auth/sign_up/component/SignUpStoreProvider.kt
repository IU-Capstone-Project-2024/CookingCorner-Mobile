package com.paranid5.cooking_corner.featrue.auth.sign_up.component

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.paranid5.cooking_corner.domain.auth.AuthRepository
import com.paranid5.cooking_corner.domain.auth.TokenInteractor
import com.paranid5.cooking_corner.domain.global_event.GlobalEventRepository
import com.paranid5.cooking_corner.featrue.auth.sign_up.component.SignUpStore.Label
import com.paranid5.cooking_corner.featrue.auth.sign_up.component.SignUpStore.State
import com.paranid5.cooking_corner.featrue.auth.sign_up.component.SignUpStore.UiIntent

internal class SignUpStoreProvider(
    private val storeFactory: StoreFactory,
    private val authRepository: AuthRepository,
    private val globalEventRepository: GlobalEventRepository,
    private val tokenInteractor: TokenInteractor,
) {
    sealed interface Msg {
        data class UpdateLoginText(val login: String) : Msg
        data class UpdatePasswordText(val password: String) : Msg
        data object UpdatePasswordVisibility : Msg
        data class UpdateConfirmPasswordText(val confirmPassword: String) : Msg
    }

    fun provide(initialState: State): SignUpStore = object :
        SignUpStore,
        Store<UiIntent, State, Label> by storeFactory.create(
            name = "SignInStore",
            initialState = initialState,
            executorFactory = {
                SignUpExecutor(
                    authRepository = authRepository,
                    globalEventRepository = globalEventRepository,
                    tokenInteractor = tokenInteractor,
                )
            },
            reducer = SignUpReducer,
        ) {}

    class Factory(
        private val storeFactory: StoreFactory,
        private val authRepository: AuthRepository,
        private val globalEventRepository: GlobalEventRepository,
        private val tokenInteractor: TokenInteractor,
    ) {
        fun create() = SignUpStoreProvider(
            storeFactory = storeFactory,
            authRepository = authRepository,
            globalEventRepository = globalEventRepository,
            tokenInteractor = tokenInteractor,
        )
    }
}