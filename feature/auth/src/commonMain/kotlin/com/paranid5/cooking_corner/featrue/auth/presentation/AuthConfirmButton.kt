package com.paranid5.cooking_corner.featrue.auth.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.paranid5.cooking_corner.ui.foundation.AppRippleButton
import com.paranid5.cooking_corner.ui.theme.AppTheme
import com.paranid5.cooking_corner.ui.utils.simpleShadow

@Composable
internal fun AuthConfirmButton(
    text: String,
    isEnabled: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) = AppRippleButton(
    onClick = onClick,
    isEnabled = isEnabled,
    rippleColor = AppTheme.colors.button.primary,
    modifier = modifier.simpleShadow(
        radius = AppTheme.dimensions.corners.minimum
    ),
    shape = RoundedCornerShape(AppTheme.dimensions.corners.small),
    colors = ButtonDefaults.buttonColors(
        containerColor = AppTheme.colors.button.primary,
        disabledContainerColor = AppTheme.colors.button.primary,
    ),
) {
    AuthButtonText(
        text = text,
        modifier = Modifier.padding(AppTheme.dimensions.padding.medium),
    )
}

@Composable
private fun AuthButtonText(
    text: String,
    modifier: Modifier = Modifier,
) = Text(
    text = text,
    modifier = modifier,
    color = AppTheme.colors.text.secondary,
    style = AppTheme.typography.h.h2,
    fontWeight = FontWeight.Bold,
    fontFamily = AppTheme.typography.InterFontFamily,
)