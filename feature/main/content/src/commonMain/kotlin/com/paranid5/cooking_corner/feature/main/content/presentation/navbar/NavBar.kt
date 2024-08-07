package com.paranid5.cooking_corner.feature.main.content.presentation.navbar

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.NavigationRail
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.home_tab
import com.paranid5.cooking_corner.core.resources.nav_bar_home
import com.paranid5.cooking_corner.core.resources.nav_bar_profile
import com.paranid5.cooking_corner.core.resources.nav_bar_search
import com.paranid5.cooking_corner.core.resources.profile_tab
import com.paranid5.cooking_corner.core.resources.search_tab
import com.paranid5.cooking_corner.feature.main.content.component.MainContentChild
import com.paranid5.cooking_corner.feature.main.content.component.MainContentUiIntent
import com.paranid5.cooking_corner.ui.theme.AppTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
internal expect fun NavBar(
    currentScreen: MainContentChild,
    onUiIntent: (MainContentUiIntent) -> Unit,
    modifier: Modifier = Modifier,
)

@Composable
internal fun NavBarMobile(
    currentScreen: MainContentChild,
    onUiIntent: (MainContentUiIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(
        topStart = AppTheme.dimensions.corners.small,
        topEnd = AppTheme.dimensions.corners.small,
    )

    BottomAppBar(
        containerColor = AppTheme.colors.background.primary,
        modifier = modifier
            .border(
                width = AppTheme.dimensions.borders.extraSmall,
                color = AppTheme.colors.button.primary,
                shape = shape,
            )
            .clip(shape),
    ) {
        NavBarItem(
            title = stringResource(Res.string.nav_bar_search),
            image = vectorResource(Res.drawable.search_tab),
            isCurrent = currentScreen is MainContentChild.Search,
            modifier = Modifier.weight(1F),
            onClick = { onUiIntent(MainContentUiIntent.ShowSearch) },
        )

        NavBarItem(
            title = stringResource(Res.string.nav_bar_home),
            image = vectorResource(Res.drawable.home_tab),
            isCurrent = currentScreen is MainContentChild.Home,
            modifier = Modifier.weight(1F),
            onClick = { onUiIntent(MainContentUiIntent.ShowHome) },
        )

        NavBarItem(
            title = stringResource(Res.string.nav_bar_profile),
            image = vectorResource(Res.drawable.profile_tab),
            isCurrent = currentScreen is MainContentChild.Profile,
            modifier = Modifier.weight(1F),
            onClick = { onUiIntent(MainContentUiIntent.ShowProfile) },
        )
    }
}

@Composable
internal fun NavBarPC(
    currentScreen: MainContentChild,
    onUiIntent: (MainContentUiIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(
        topEnd = AppTheme.dimensions.corners.small,
        bottomEnd = AppTheme.dimensions.corners.small,
    )

    NavigationRail(
        containerColor = AppTheme.colors.background.primary,
        modifier = modifier
            .border(
                width = AppTheme.dimensions.borders.extraSmall,
                color = AppTheme.colors.button.primary,
                shape = shape,
            )
            .clip(shape),
    ) {
        val itemModifier = Modifier
            .weight(1F)
            .padding(horizontal = AppTheme.dimensions.padding.small)

        NavBarItem(
            title = stringResource(Res.string.nav_bar_search),
            image = vectorResource(Res.drawable.search_tab),
            isCurrent = currentScreen is MainContentChild.Search,
            modifier = itemModifier,
            onClick = { onUiIntent(MainContentUiIntent.ShowSearch) },
        )

        NavBarItem(
            title = stringResource(Res.string.nav_bar_home),
            image = vectorResource(Res.drawable.home_tab),
            isCurrent = currentScreen is MainContentChild.Home,
            modifier = itemModifier,
            onClick = { onUiIntent(MainContentUiIntent.ShowHome) },
        )

        NavBarItem(
            title = stringResource(Res.string.nav_bar_profile),
            image = vectorResource(Res.drawable.profile_tab),
            isCurrent = currentScreen is MainContentChild.Profile,
            modifier = itemModifier,
            onClick = { onUiIntent(MainContentUiIntent.ShowProfile) },
        )
    }
}
