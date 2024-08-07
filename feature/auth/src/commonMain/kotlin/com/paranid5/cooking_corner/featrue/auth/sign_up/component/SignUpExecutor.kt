package com.paranid5.cooking_corner.featrue.auth.sign_up.component

import arrow.core.Either
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.paranid5.cooking_corner.core.common.AppDispatchers
import com.paranid5.cooking_corner.domain.auth.AuthRepository
import com.paranid5.cooking_corner.domain.auth.TokenInteractor
import com.paranid5.cooking_corner.domain.auth.TokenInteractor.TokenResult
import com.paranid5.cooking_corner.domain.global_event.GlobalEventRepository
import com.paranid5.cooking_corner.domain.global_event.sendSnackbar
import com.paranid5.cooking_corner.domain.snackbar.SnackbarMessage
import com.paranid5.cooking_corner.featrue.auth.sign_up.component.SignUpStore.Label
import com.paranid5.cooking_corner.featrue.auth.sign_up.component.SignUpStore.State
import com.paranid5.cooking_corner.featrue.auth.sign_up.component.SignUpStore.UiIntent
import com.paranid5.cooking_corner.featrue.auth.sign_up.component.SignUpStoreProvider.Msg
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class SignUpExecutor(
    private val authRepository: AuthRepository,
    private val globalEventRepository: GlobalEventRepository,
    private val tokenInteractor: TokenInteractor,
) : CoroutineExecutor<UiIntent, Unit, State, Msg, Label>() {
    override fun executeIntent(intent: UiIntent) {
        when (intent) {
            is UiIntent.Back -> publish(Label.Back)

            is UiIntent.ConfirmCredentials -> scope.launch {
                checkCredentials(
                    unhandledErrorSnackbar = intent.unhandledErrorSnackbar,
                    invalidCredentialsSnackbar = intent.invalidCredentialsSnackbar,
                )
            }

            is UiIntent.UpdateLoginText -> dispatch(Msg.UpdateLoginText(intent.login))

            is UiIntent.UpdatePasswordText -> dispatch(Msg.UpdatePasswordText(intent.password))

            is UiIntent.UpdatePasswordVisibility -> dispatch(Msg.UpdatePasswordVisibility)

            is UiIntent.UpdateConfirmPasswordText ->
                dispatch(Msg.UpdateConfirmPasswordText(intent.confirmPassword))
        }
    }

    private suspend fun checkCredentials(
        unhandledErrorSnackbar: SnackbarMessage,
        invalidCredentialsSnackbar: SnackbarMessage,
    ) = when (
        val registerRes = withContext(AppDispatchers.Data) {
            authRepository.register(
                username = state().login,
                password = state().password,
            )
        }
    ) {
        is Either.Left -> {
            registerRes.value.printStackTrace()
            sendSnackbar(snackbar = unhandledErrorSnackbar)
        }

        is Either.Right -> when (registerRes.value) {
            is Either.Left -> sendSnackbar(snackbar = invalidCredentialsSnackbar)
            is Either.Right -> tryAcquireTokens(unhandledErrorSnackbar = unhandledErrorSnackbar)
        }
    }

    private suspend fun tryAcquireTokens(unhandledErrorSnackbar: SnackbarMessage) = when (
        tokenInteractor.tryAcquireTokens(
            login = state().login,
            password = state().password,
        )
    ) {
        is TokenResult.CredentialsMissing -> error("Credentials missing")

        is TokenResult.Success -> {
            authRepository.storeLogin(state().login)
            authRepository.storePassword(state().password)
            publish(Label.ConfirmedCredentials)
        }

        is TokenResult.InvalidPassword, is TokenResult.UnhandledError ->
            sendSnackbar(snackbar = unhandledErrorSnackbar)
    }

    private suspend fun sendSnackbar(snackbar: SnackbarMessage) =
        globalEventRepository.sendSnackbar(snackbar)
}