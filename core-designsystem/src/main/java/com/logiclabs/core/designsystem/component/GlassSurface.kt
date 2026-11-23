package com.logiclabs.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.logiclabs.core.designsystem.theme.Dimens
import com.logiclabs.core.designsystem.theme.GlassHairline
import com.logiclabs.core.designsystem.theme.GlassSpecular
import com.logiclabs.core.designsystem.theme.GlassSurfaceColor
import org.jetbrains.skia.FilterTileMode
import org.jetbrains.skia.ImageFilter

private val SpecularGradient = Brush.verticalGradient(
    colors = listOf(GlassSpecular, Color.Transparent)
)

private val DesktopBlurEffect = runCatching {
    ImageFilter.makeBlur(16f, 16f, FilterTileMode.DECAL).asComposeRenderEffect()
}.getOrNull()

/**
 * Frosted panel used for floating HUD chrome (command bar, context ribbon, modals).
 * Uses Skia's ImageFilter blur on the specular backdrop layer only so foreground text stays razor sharp.
 */
@Composable
fun GlassSurface(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(Dimens.RadiusLg),
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .clip(shape)
            .background(GlassSurfaceColor)
            .border(Dimens.Hairline, GlassHairline, shape)
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .then(
                    if (DesktopBlurEffect != null) {
                        Modifier.graphicsLayer { renderEffect = DesktopBlurEffect }
                    } else {
                        Modifier
                    }
                )
                .background(SpecularGradient)
        )
        content()
    }
}

val GlassSpecularHeight = 24.dp
