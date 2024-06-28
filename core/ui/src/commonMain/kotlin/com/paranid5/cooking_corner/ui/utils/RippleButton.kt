package com.paranid5.cooking_corner.ui.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.paranid5.cooking_corner.ui.theme.AppTheme

@Composable
fun RippleButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    rippleColor: Color = AppTheme.colors.orange,
    rippleBounded: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = ButtonDefaults.shape,
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        shape = shape,
        interactionSource = interactionSource,
        border = border,
        colors = colors,
        elevation = elevation,
        modifier = modifier
            .clip(shape)
            .indication(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    bounded = rippleBounded,
                    color = rippleColor,
                )
            ),
        content = content,
    )
}