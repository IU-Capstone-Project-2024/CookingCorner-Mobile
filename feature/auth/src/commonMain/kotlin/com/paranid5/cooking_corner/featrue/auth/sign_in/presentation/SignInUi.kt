package com.paranid5.cooking_corner.featrue.auth.sign_in.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.auth_login
import com.paranid5.cooking_corner.core.resources.auth_password
import com.paranid5.cooking_corner.core.resources.auth_sign_in
import com.paranid5.cooking_corner.core.resources.auth_sign_up
import com.paranid5.cooking_corner.core.resources.auth_wrong_password
import com.paranid5.cooking_corner.featrue.auth.presentation.AuthConfirmButton
import com.paranid5.cooking_corner.featrue.auth.presentation.AuthEditText
import com.paranid5.cooking_corner.featrue.auth.presentation.PasswordHandling
import com.paranid5.cooking_corner.featrue.auth.sign_in.component.SignInComponent
import com.paranid5.cooking_corner.featrue.auth.sign_in.component.SignInStore.State
import com.paranid5.cooking_corner.featrue.auth.sign_in.component.SignInStore.UiIntent
import com.paranid5.cooking_corner.ui.foundation.CookingCornerLabel
import com.paranid5.cooking_corner.ui.foundation.CookingIcon
import com.paranid5.cooking_corner.ui.theme.AppTheme
import org.jetbrains.compose.resources.stringResource

private val COOKING_ICON_WIDTH = 105.dp
private val COOKING_ICON_HEIGHT = 134.dp

@Composable
internal fun SignInUi(
    component: SignInComponent,
    modifier: Modifier = Modifier,
) {
    val state by component.stateFlow.collectAsState()
    val onUiIntent = component::onUiIntent

    Box(modifier) {
        SignInContent(
            state = state,
            onUiIntent = onUiIntent,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.dimensions.padding.enormous),
        )

        SignUpButton(
            onUiIntent = onUiIntent,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = AppTheme.dimensions.padding.extraMedium),
        )

        if (state.isErrorDialogVisible) {
            // TODO: Error dialog
        }
    }
}

@Composable
private fun SignInContent(
    state: State,
    onUiIntent: (UiIntent) -> Unit,
    modifier: Modifier = Modifier,
) = Column(modifier) {
    CookingIcon(
        Modifier
            .width(COOKING_ICON_WIDTH)
            .height(COOKING_ICON_HEIGHT)
            .align(Alignment.CenterHorizontally),
    )

    Spacer(Modifier.height(AppTheme.dimensions.padding.extraSmall))

    CookingCornerLabel(Modifier.align(Alignment.CenterHorizontally))

    Spacer(Modifier.height(AppTheme.dimensions.padding.large))

    AuthEditText(
        value = state.login,
        onValueChange = { onUiIntent(UiIntent.UpdateLoginText(login = it)) },
        placeholder = stringResource(Res.string.auth_login),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.dimensions.padding.extraMedium),
    )

    Spacer(Modifier.height(AppTheme.dimensions.padding.extraBig))

    AuthEditText(
        value = state.password,
        onValueChange = { onUiIntent(UiIntent.UpdatePasswordText(password = it)) },
        placeholder = stringResource(Res.string.auth_password),
        passwordHandling = PasswordHandling(
            isPasswordVisible = state.isPasswordVisible,
            isPasswordInvalid = state.isPasswordInvalid,
            onPasswordVisibilityChanged = { onUiIntent(UiIntent.UpdatePasswordVisibility) },
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.dimensions.padding.extraMedium),
    )

    Spacer(Modifier.height(AppTheme.dimensions.padding.extraBig))

    AuthConfirmButton(
        isEnabled = state.isInputNotEmpty,
        text = stringResource(
            confirmButtonTextRes(areCredentialsInvalid = state.isPasswordInvalid)
        ),
        onClick = { onUiIntent(UiIntent.ConfirmCredentials) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.dimensions.padding.extraMedium),
    )
}

@Composable
private fun SignUpButton(
    onUiIntent: (UiIntent) -> Unit,
    modifier: Modifier = Modifier,
) = Button(
    modifier = modifier,
    onClick = { onUiIntent(UiIntent.ShowSignUp) },
    elevation = null,
    colors = ButtonDefaults.buttonColors(
        backgroundColor = Color.Transparent,
        disabledBackgroundColor = Color.Transparent,
    ),
) {
    Text(
        text = stringResource(Res.string.auth_sign_up),
        color = AppTheme.colors.text.tertiriary,
        style = AppTheme.typography.h.h3,
        fontFamily = AppTheme.typography.InterFontFamily,
    )
}

private fun confirmButtonTextRes(areCredentialsInvalid: Boolean) = when {
    areCredentialsInvalid -> Res.string.auth_wrong_password
    else -> Res.string.auth_sign_in
}