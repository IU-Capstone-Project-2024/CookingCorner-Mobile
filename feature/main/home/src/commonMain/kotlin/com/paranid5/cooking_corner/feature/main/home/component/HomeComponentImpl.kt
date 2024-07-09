package com.paranid5.cooking_corner.feature.main.home.component

import androidx.paging.PagingData
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnPause
import com.arkivanov.essenty.lifecycle.doOnResume
import com.arkivanov.mvikotlin.core.binder.BinderLifecycleMode
import com.arkivanov.mvikotlin.extensions.coroutines.bind
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.paranid5.cooking_corner.component.componentScope
import com.paranid5.cooking_corner.component.getComponentStore
import com.paranid5.cooking_corner.feature.main.home.component.HomeComponent.BackResult
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.Label
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.State
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.UiIntent
import com.paranid5.cooking_corner.ui.entity.RecipeUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

internal class HomeComponentImpl(
    componentContext: ComponentContext,
    private val storeFactory: HomeStoreProvider.Factory,
    private val onBack: (BackResult) -> Unit,
) : HomeComponent, ComponentContext by componentContext {
    private val componentStore = getComponentStore(
        defaultState = State(),
        storeFactory = { storeFactory.create().provide(initialState = it) }
    )

    private val store = componentStore.value

    @OptIn(ExperimentalCoroutinesApi::class)
    override val stateFlow = store.stateFlow

    private var subscribeStateJob: Job? = null

    init {
        bind(lifecycle, BinderLifecycleMode.CREATE_DESTROY) {
            store.labels bindTo ::onLabel
        }

        doOnResume {
            subscribeStateJob = subscribeOnStateUpdates()
        }

        doOnPause {
            subscribeStateJob?.cancel()
            subscribeStateJob = null
        }
    }

    override fun onUiIntent(intent: UiIntent) = store.accept(intent)

    private fun onLabel(label: Label) = when (label) {
        is Label.ShowAddRecipe ->
            onBack(BackResult.ShowAddRecipe)

        is Label.ShowGenerateRecipe ->
            onBack(BackResult.ShowImportRecipe)

        is Label.ShowRecipe ->
            onBack(BackResult.ShowRecipeDetails(recipeUiState = label.recipeUiState))
    }

    private fun subscribeOnStateUpdates() = componentScope.launch {
        stateFlow
            .map { it.selectedCategoryTitle to it.isAscendingOrder }
            .distinctUntilChanged()
            .collectLatest { onUiIntent(UiIntent.LoadMyRecipes) }
    }

    class Factory(private val storeFactory: HomeStoreProvider.Factory) : HomeComponent.Factory {
        override fun create(
            componentContext: ComponentContext,
            onBack: (BackResult) -> Unit,
        ): HomeComponent = HomeComponentImpl(
            componentContext = componentContext,
            storeFactory = storeFactory,
            onBack = onBack,
        )
    }
}

private fun stub() = flowOf(
    PagingData.from(
        listOf(
            RecipeUiState(
                title = "Boiled shrimps",
                rating = 4.8F,
                preparingTime = 5,
                cookingTime = 10,
                isLiked = true,
                author = "OlgaLove",
            ),
            RecipeUiState(
                title = "Chicken in soy sauce",
                rating = 4.5F,
                preparingTime = 10,
                cookingTime = 10,
                isLiked = false,
                author = "OlgaLove",
            ),
            RecipeUiState(
                title = "Easy Lasagna in Pan",
                rating = 4.1F,
                preparingTime = 20,
                cookingTime = 20,
                isLiked = true,
                author = "OlgaLove",
            ),
            RecipeUiState(
                title = "Charlotte in a slow cooker",
                rating = 4.9F,
                preparingTime = 20,
                cookingTime = 20,
                isLiked = false,
                author = "OlgaLove",
            ),
            RecipeUiState(
                title = "Boiled shrimps",
                rating = 4.8F,
                preparingTime = 5,
                cookingTime = 10,
                isLiked = true,
                author = "OlgaLove",
            ),
            RecipeUiState(
                title = "Chicken in soy sauce",
                rating = 4.5F,
                preparingTime = 10,
                cookingTime = 10,
                isLiked = false,
                author = "OlgaLove",
            ),
            RecipeUiState(
                title = "Easy Lasagna in Pan",
                rating = 4.1F,
                preparingTime = 20,
                cookingTime = 20,
                isLiked = true,
                author = "OlgaLove",
            ),
            RecipeUiState(
                title = "Charlotte in a slow cooker",
                rating = 4.9F,
                preparingTime = 20,
                cookingTime = 20,
                isLiked = false,
                author = "OlgaLove",
            ),
            RecipeUiState(
                title = "Boiled shrimps",
                rating = 4.8F,
                preparingTime = 5,
                cookingTime = 10,
                isLiked = true,
                author = "OlgaLove",
            ),
            RecipeUiState(
                title = "Chicken in soy sauce",
                rating = 4.5F,
                preparingTime = 10,
                cookingTime = 10,
                isLiked = false,
                author = "OlgaLove",
            ),
            RecipeUiState(
                title = "Easy Lasagna in Pan",
                rating = 4.1F,
                preparingTime = 20,
                cookingTime = 20,
                isLiked = true,
                author = "OlgaLove",
            ),
            RecipeUiState(
                title = "Charlotte in a slow cooker",
                rating = 4.9F,
                preparingTime = 20,
                cookingTime = 20,
                isLiked = false,
                author = "OlgaLove",
            ),
            RecipeUiState(
                title = "Boiled shrimps",
                rating = 4.8F,
                preparingTime = 5,
                cookingTime = 10,
                isLiked = true,
                author = "OlgaLove",
            ),
            RecipeUiState(
                title = "Chicken in soy sauce",
                rating = 4.5F,
                preparingTime = 10,
                cookingTime = 10,
                isLiked = false,
                author = "OlgaLove",
            ),
            RecipeUiState(
                title = "Easy Lasagna in Pan",
                rating = 4.1F,
                preparingTime = 20,
                cookingTime = 20,
                isLiked = true,
                author = "OlgaLove",
            ),
            RecipeUiState(
                title = "Charlotte in a slow cooker",
                rating = 4.9F,
                preparingTime = 20,
                cookingTime = 20,
                isLiked = false,
                author = "OlgaLove",
            )
        )
    )
)