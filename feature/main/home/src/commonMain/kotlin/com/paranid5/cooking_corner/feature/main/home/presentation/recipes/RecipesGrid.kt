package com.paranid5.cooking_corner.feature.main.home.presentation.recipes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.something_went_wrong
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.State
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.UiIntent
import com.paranid5.cooking_corner.feature.main.recipe.presentation.brief.RecipeItem
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.foundation.AppProgressIndicator
import com.paranid5.cooking_corner.ui.foundation.placeholder.AppErrorStub
import com.paranid5.cooking_corner.ui.theme.AppTheme
import com.paranid5.cooking_corner.ui.utils.clickableWithRipple
import com.paranid5.cooking_corner.ui.utils.pxToDp
import org.jetbrains.compose.resources.stringResource

private const val MIN_RECIPES_IN_ROW = 2
private val RECIPE_MAX_WIDTH = 185.dp
private val RECIPE_HEIGHT = 280.dp
private val PADDING_BETWEEN_RECIPES = 8.dp

@Composable
internal fun RecipesGrid(
    state: State,
    onUiIntent: (UiIntent) -> Unit,
    modifier: Modifier = Modifier
) = Box(modifier) {
    when (state.recipesUiState) {
        is UiState.Data, is UiState.Refreshing, is UiState.Success ->
            RecipesGridContent(
                state = state,
                onUiIntent = onUiIntent,
                modifier = Modifier.fillMaxSize()
            )

        is UiState.Error ->
            AppErrorStub(
                errorMessage = stringResource(Res.string.something_went_wrong),
                modifier = Modifier.align(Alignment.Center),
            )

        is UiState.Loading, is UiState.Undefined ->
            AppProgressIndicator(Modifier.align(Alignment.Center))
    }
}

@Composable
private fun RecipesGridContent(
    state: State,
    onUiIntent: (UiIntent) -> Unit,
    modifier: Modifier = Modifier,
) = when {
    state.shownRecipes.isEmpty() -> NoItemsPlaceholder(
        modifier
            .verticalScroll(rememberScrollState())
            .padding(top = AppTheme.dimensions.padding.extraLarge),
    )

    else -> RecipesGridContentImpl(
        state = state,
        onUiIntent = onUiIntent,
        modifier = modifier,
    )
}

@Composable
private fun RecipesGridContentImpl(
    state: State,
    onUiIntent: (UiIntent) -> Unit,
    modifier: Modifier = Modifier,
) = BoxWithConstraints(modifier) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(PADDING_BETWEEN_RECIPES),
        horizontalArrangement = Arrangement.spacedBy(PADDING_BETWEEN_RECIPES),
        columns = GridCells.Adaptive(getMinCellWidth(constraints.maxWidth)),
    ) {
        items(items = state.shownRecipes) { recipe ->
            RecipeItem(
                recipe = recipe,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(RECIPE_HEIGHT)
                    .clickableWithRipple(bounded = true) {
                        onUiIntent(UiIntent.ShowRecipe(recipeId = recipe.id))
                    },
            ) { modifier ->
                FavouritesButton(
                    recipeUiState = recipe,
                    onUiIntent = onUiIntent,
                    modifier = modifier,
                )
            }
        }
    }
}

@Composable
private fun getMinCellWidth(screenWidth: Int): Dp {
    val screenWidthDp = screenWidth.pxToDp()
    return remember(screenWidthDp) {
        minOf(
            screenWidthDp / MIN_RECIPES_IN_ROW - PADDING_BETWEEN_RECIPES,
            RECIPE_MAX_WIDTH
        )
    }
}
