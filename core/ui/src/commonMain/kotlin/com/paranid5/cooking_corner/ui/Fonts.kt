package com.paranid5.cooking_corner.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.inter
import com.paranid5.cooking_corner.core.resources.raleway
import org.jetbrains.compose.resources.Font

internal val InterFont
    @Composable
    get() = FontFamily(Font(Res.font.inter))

internal val RalewayFont
    @Composable
    get() = FontFamily(Font(Res.font.raleway))