package com.paranid5.cooking_corner.feature.main.home.presentation.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.ic_add
import com.paranid5.cooking_corner.core.resources.ic_descending_filter
import com.paranid5.cooking_corner.core.resources.ic_import
import com.paranid5.cooking_corner.core.resources.like
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.State
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.UiIntent
import com.paranid5.cooking_corner.ui.foundation.AppIconButton
import com.paranid5.cooking_corner.ui.theme.AppTheme
import org.jetbrains.compose.resources.vectorResource

@Composable
internal fun HomeTopBar(
    state: State,
    onUiIntent: (UiIntent) -> Unit,
    modifier: Modifier = Modifier,
) = Column(modifier) {
    TopBarBorder(Modifier.fillMaxWidth())

    HomeTopBarImpl(
        state = state,
        onUiIntent = onUiIntent,
        modifier = Modifier.fillMaxWidth()
    )

    TopBarBorder(Modifier.fillMaxWidth())
}

@Composable
private fun HomeTopBarImpl(
    state: State,
    onUiIntent: (UiIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val spinnerShape = RoundedCornerShape(AppTheme.dimensions.corners.small)
    val margin = AppTheme.dimensions.padding.extraSmall

    Row(
        horizontalArrangement = Arrangement.spacedBy(margin),
        modifier = modifier
            .background(AppTheme.colors.background.primaryDarker)
            .padding(horizontal = margin)
    ) {
        AppIconButton(
            icon = vectorResource(Res.drawable.ic_add),
            modifier = Modifier.align(Alignment.CenterVertically),
            onClick = { onUiIntent(UiIntent.AddRecipe) },
        )

        AppIconButton(
            icon = vectorResource(Res.drawable.ic_import),
            modifier = Modifier.align(Alignment.CenterVertically),
            onClick = { onUiIntent(UiIntent.ImportRecipe) },
        )

        CategorySpinner(
            state = state,
            onUiIntent = onUiIntent,
            modifier = Modifier
                .weight(1F)
                .align(Alignment.CenterVertically)
                .padding(vertical = AppTheme.dimensions.padding.small)
                .clip(spinnerShape)
                .border(
                    width = AppTheme.dimensions.borders.minimum,
                    color = AppTheme.colors.button.primary,
                    shape = spinnerShape,
                ),
        )

        AppIconButton(
            icon = vectorResource(Res.drawable.ic_descending_filter),
            modifier = Modifier.align(Alignment.CenterVertically),
            onClick = { onUiIntent(UiIntent.DescendingFilterClick) },
        )

        AppIconButton(
            icon = vectorResource(Res.drawable.like),
            tint = AppTheme.colors.orange,
            modifier = Modifier.align(Alignment.CenterVertically),
            onClick = { onUiIntent(UiIntent.ShowFavourites) },
        )
    }
}

@Composable
private fun TopBarBorder(modifier: Modifier = Modifier) = Spacer(
    modifier
        .height(AppTheme.dimensions.borders.minimum)
        .background(AppTheme.colors.button.primary)
)
