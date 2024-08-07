package com.paranid5.cooking_corner.featrue.auth.sign_up.component

import androidx.compose.runtime.Immutable
import com.arkivanov.mvikotlin.core.store.Store
import com.paranid5.cooking_corner.domain.snackbar.SnackbarMessage
import com.paranid5.cooking_corner.featrue.auth.sign_up.component.SignUpStore.Label
import com.paranid5.cooking_corner.featrue.auth.sign_up.component.SignUpStore.State
import com.paranid5.cooking_corner.featrue.auth.sign_up.component.SignUpStore.UiIntent
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

internal interface SignUpStore : Store<UiIntent, State, Label> {
    private companion object {
        const val USERNAME_MIN_INPUT_LENGTH = 6
    }

    sealed interface UiIntent {
        data object Back : UiIntent

        data class UpdateLoginText(val login: String) : UiIntent

        data class UpdatePasswordText(val password: String) : UiIntent

        data object UpdatePasswordVisibility : UiIntent

        data class UpdateConfirmPasswordText(val confirmPassword: String) : UiIntent

        data class ConfirmCredentials(
            val unhandledErrorSnackbar: SnackbarMessage,
            val invalidCredentialsSnackbar: SnackbarMessage,
        ) : UiIntent
    }

    @Serializable
    @Immutable
    data class State(
        val login: String,
        val password: String,
        val confirmPassword: String,
        val isPasswordVisible: Boolean,
    ) {
        @Transient
        val isUsernameShort = login.length < USERNAME_MIN_INPUT_LENGTH

        @Transient
        val isUsernameShortErrorVisible = isUsernameShort && login.isNotEmpty()

        @Transient
        val isPasswordShort = password.length < USERNAME_MIN_INPUT_LENGTH

        @Transient
        val isPasswordShortErrorVisible = isPasswordShort && password.isNotEmpty()

        @Transient
        val isInputNotShort = isUsernameShort.not() && isPasswordShort.not()

        @Transient
        val isPasswordConfirmed = password == confirmPassword

        @Transient
        val isConfirmedPasswordErrorVisible =
            isPasswordConfirmed.not() && confirmPassword.isNotEmpty()

        constructor() : this(
            login = "",
            password = "",
            confirmPassword = "",
            isPasswordVisible = false,
        )
    }

    sealed interface Label {
        data object Back : Label
        data object ConfirmedCredentials : Label
    }
}