package com.logiclabs.core.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

/**
 * Engineering monospace font family: JetBrains Mono (bundled in classpath resources).
 */
val JetBrainsMono = FontFamily(
    Font("font/jetbrains_mono_medium.ttf", FontWeight.Normal),
    Font("font/jetbrains_mono_medium.ttf", FontWeight.Medium),
    Font("font/jetbrains_mono_medium.ttf", FontWeight.SemiBold),
    Font("font/jetbrains_mono_medium.ttf", FontWeight.Bold)
)

/**
 * Hardware interface sans font family: Inter (bundled in classpath resources).
 */
val Inter = FontFamily(
    Font("font/inter_semibold.ttf", FontWeight.Normal),
    Font("font/inter_semibold.ttf", FontWeight.Medium),
    Font("font/inter_semibold.ttf", FontWeight.SemiBold),
    Font("font/inter_semibold.ttf", FontWeight.Bold)
)

/**
 * Type scale for the Logic Labs bench.
 */
object LogicLabsType {

    val TechnicalXs: TextStyle = TextStyle(
        fontFamily = JetBrainsMono,
        fontWeight = FontWeight.Medium,
        fontSize = 9.sp,
        lineHeight = 11.sp
    )

    val TechnicalSm: TextStyle = TextStyle(
        fontFamily = JetBrainsMono,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 13.sp
    )

    val TechnicalMd: TextStyle = TextStyle(
        fontFamily = JetBrainsMono,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp
    )

    val TechnicalLg: TextStyle = TextStyle(
        fontFamily = JetBrainsMono,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 18.sp
    )

    val HashMono: TextStyle = TextStyle(
        fontFamily = JetBrainsMono,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.5.sp
    )

    val DisplayLg: TextStyle = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 34.sp,
        letterSpacing = (-0.01).em
    )

    val TitleMd: TextStyle = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = (-0.01).em
    )

    val TitleSm: TextStyle = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp,
        lineHeight = 20.sp,
        letterSpacing = (-0.01).em
    )

    val BodyMd: TextStyle = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = (-0.01).em
    )

    val BodySm: TextStyle = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 17.sp,
        letterSpacing = (-0.01).em
    )

    val LabelSm: TextStyle = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 14.sp
    )

    val SwitchPlate: TextStyle = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.SemiBold,
        fontSize = 9.sp,
        lineHeight = 12.sp,
        letterSpacing = 0.12.em
    )

    val SwitchPlateLg: TextStyle = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.SemiBold,
        fontSize = 10.sp,
        lineHeight = 13.sp,
        letterSpacing = 0.12.em
    )
}

val LogicLabsTypography: Typography = Typography(
    displayLarge = LogicLabsType.DisplayLg,
    titleLarge = LogicLabsType.TitleMd,
    titleMedium = LogicLabsType.TitleSm,
    titleSmall = LogicLabsType.LabelSm,
    bodyLarge = LogicLabsType.BodyMd,
    bodyMedium = LogicLabsType.BodyMd,
    bodySmall = LogicLabsType.BodySm,
    labelLarge = LogicLabsType.LabelSm,
    labelMedium = LogicLabsType.SwitchPlateLg,
    labelSmall = LogicLabsType.SwitchPlate
)
