package com.paranid5.cooking_corner.feature.main.home.component

import androidx.paging.PagingData
import com.arkivanov.decompose.ComponentContext
import com.paranid5.cooking_corner.component.StateSource
import com.paranid5.cooking_corner.component.UiIntentHandler
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.State
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.UiIntent
import com.paranid5.cooking_corner.ui.entity.RecipeUiState
import kotlinx.coroutines.flow.Flow

interface HomeComponent : StateSource<State>, UiIntentHandler<UiIntent> {
    val recepiesPagedFlow: Flow<PagingData<RecipeUiState>>

    sealed interface BackResult {
        data object Dismiss : BackResult
        data class ShowRecipeDetails(val recipeUiState: RecipeUiState) : BackResult
    }

    interface Factory {
        fun create(
            componentContext: ComponentContext,
            onBack: (BackResult) -> Unit,
        ): HomeComponent
    }
}