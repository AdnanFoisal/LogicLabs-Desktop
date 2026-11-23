package com.logiclabs.feature.breadboard.canvas

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.asComposeCanvas
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import com.logiclabs.core.designsystem.theme.BenchPalette
import com.logiclabs.core.designsystem.theme.BenchPaletteState
import org.jetbrains.skia.Picture
import org.jetbrains.skia.PictureRecorder
import org.jetbrains.skia.Rect as SkiaRect

/**
 * Records the static half of the faceplate into a Skia [Picture] and replays it as a single draw op.
 */
class BoardCache {

    private var picture: Picture? = null
    private var keyBucket = Float.NaN
    private var keyDensity = 0f
    private var keyWidth = 0f
    private var keyHeight = 0f
    private var keyPalette: BenchPalette? = null

    var recordings: Int = 0
        private set

    fun drawStatic(
        scope: DrawScope,
        mapper: BreadboardGeometryMapper,
        density: Float,
        scale: Float
    ) {
        val bucket = bucketOf(scale)
        val w = mapper.boardRight
        val h = mapper.boardBottom
        val palette = BenchPaletteState.value

        val stale = picture == null ||
            bucket != keyBucket ||
            density != keyDensity ||
            w != keyWidth ||
            h != keyHeight ||
            palette !== keyPalette

        if (stale) record(mapper, density, bucket, w, h, palette)

        val pic = picture ?: return
        scope.drawIntoCanvas { it.nativeCanvas.drawPicture(pic) }
    }

    private fun record(
        mapper: BreadboardGeometryMapper,
        density: Float,
        bucket: Float,
        width: Float,
        height: Float,
        palette: BenchPalette
    ) {
        picture?.close()
        val recorder = PictureRecorder()
        val recWidth = kotlin.math.ceil(width).toFloat().coerceAtLeast(1f)
        val recHeight = kotlin.math.ceil(height).toFloat().coerceAtLeast(1f)
        val skiaCanvas = recorder.beginRecording(SkiaRect.makeWH(recWidth, recHeight))
        val pic: Picture = try {
            CanvasDrawScope().draw(
                density = Density(density),
                layoutDirection = LayoutDirection.Ltr,
                canvas = skiaCanvas.asComposeCanvas(),
                size = Size(width, height)
            ) {
                FaceplateRenderer.drawStatic(
                    scope = this,
                    mapper = mapper,
                    density = density,
                    zoomScale = bucket,
                    viewport = mapper.boardBounds()
                )
            }
            recorder.finishRecordingAsPicture()
        } finally {
            recorder.close()
        }

        picture = pic
        keyBucket = bucket
        keyDensity = density
        keyWidth = width
        keyHeight = height
        keyPalette = palette
        recordings++
    }

    fun release() {
        picture?.close()
        picture = null
        keyBucket = Float.NaN
    }

    companion object {
        const val BUCKET_STEP = 0.25f

        fun bucketOf(scale: Float): Float =
            (kotlin.math.floor(scale / BUCKET_STEP) * BUCKET_STEP).coerceIn(BUCKET_STEP, 4.0f)
    }
}
