package com.logiclabs.core.designsystem.canvas

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import org.jetbrains.skia.Canvas as SkiaCanvas
import org.jetbrains.skia.Data
import org.jetbrains.skia.Font
import org.jetbrains.skia.FontMgr
import org.jetbrains.skia.FontStyle
import org.jetbrains.skia.Paint
import org.jetbrains.skia.PaintMode
import org.jetbrains.skia.Typeface

enum class TextAlignMode { LEFT, CENTER, RIGHT }

/**
 * Shared Skia typeface loader using the bundled JetBrains Mono and Inter fonts from classpath
 * resources so Windows 10/11, Linux, and macOS all render identical crisp canvas typography.
 */
object DesktopTypefaces {

    val mono: Typeface? by lazy {
        loadFromResource("font/jetbrains_mono_medium.ttf")
            ?: runCatching { FontMgr.default.legacyMakeTypeface("monospace", FontStyle.NORMAL) }.getOrNull()
    }

    val sans: Typeface? by lazy {
        loadFromResource("font/inter_semibold.ttf")
            ?: runCatching { FontMgr.default.legacyMakeTypeface("sans-serif", FontStyle.NORMAL) }.getOrNull()
    }

    private fun loadFromResource(path: String): Typeface? = runCatching {
        val stream = DesktopTypefaces::class.java.classLoader?.getResourceAsStream(path)
            ?: return null
        val bytes: ByteArray = stream.use { it.readBytes() }
        FontMgr.default.makeFromData(Data.makeFromBytes(bytes))
    }.getOrNull()
}

/**
 * Allocation-light Skia text painter mirroring the `android.graphics.Paint` contract used across
 * the breadboard, DIP package, loupe, schematic, and coursework renderers.
 */
class DesktopTextPaint(
    color: Color = Color.White,
    textSize: Float = 12f,
    bold: Boolean = false,
    monospace: Boolean = true,
    var align: TextAlignMode = TextAlignMode.LEFT
) {
    private val skiaPaint = Paint().apply {
        isAntiAlias = true
        mode = PaintMode.FILL
        this.color = color.toArgb()
    }

    private var currentSize: Float = textSize
    private var font: Font = createFont(textSize, bold, monospace)

    var color: Int
        get() = skiaPaint.color
        set(value) {
            skiaPaint.color = value
        }

    var textSize: Float
        get() = currentSize
        set(value) {
            if (value != currentSize) {
                currentSize = value
                font.size = value
            }
        }

    fun setColor(c: Color) {
        skiaPaint.color = c.toArgb()
    }

    private fun createFont(size: Float, bold: Boolean, monospace: Boolean): Font {
        val tf = if (monospace) DesktopTypefaces.mono else DesktopTypefaces.sans
        return Font(tf, size).apply {
            isSubpixel = true
            isEmboldened = bold
        }
    }

    fun measureText(text: String): Float {
        if (text.isEmpty()) return 0f
        return font.measureTextWidth(text, skiaPaint)
    }

    fun ascent(): Float = font.metrics.ascent

    fun descent(): Float = font.metrics.descent

    fun lineHeight(): Float {
        val m = font.metrics
        return (m.descent - m.ascent + m.leading).coerceAtLeast(currentSize * 1.15f)
    }

    fun drawText(canvas: SkiaCanvas, text: String, x: Float, y: Float) {
        if (text.isEmpty()) return
        val drawX = when (align) {
            TextAlignMode.LEFT -> x
            TextAlignMode.CENTER -> x - measureText(text) * 0.5f
            TextAlignMode.RIGHT -> x - measureText(text)
        }
        canvas.drawString(text, drawX, y, font, skiaPaint)
    }
}

/** Drop-in extension mirroring Android's `Canvas.drawText(text, x, y, paint)`. */
fun SkiaCanvas.drawText(text: String, x: Float, y: Float, paint: DesktopTextPaint) {
    paint.drawText(this, text, x, y)
}
