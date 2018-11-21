package it.android.luca.movieapp.detail.ui

import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.v7.graphics.Palette
import android.widget.ImageView
import com.bumptech.glide.request.target.ImageViewTarget

class MoviePosterTarget(val poster: ImageView, val activity: DynamicColorsActivity) :
    ImageViewTarget<Drawable>(poster) {
    override fun setResource(resource: android.graphics.drawable.Drawable?) {
        resource?.let {
            setImage(it)
            generatePalette(it)
        }
    }

    private fun setImage(resource: android.graphics.drawable.Drawable) {
        poster.setBackgroundDrawable(resource)
    }

    private fun generatePalette(resource: android.graphics.drawable.Drawable) {
        val b = (resource.current as BitmapDrawable).bitmap
        Palette.from(b).clearFilters().generate(ColorExtractor(activity))
    }
}

class ColorExtractor(private val activity: DynamicColorsActivity) : Palette.PaletteAsyncListener {

    val black = Color.BLACK
    val white = Color.WHITE
    var bgColor = black
    var textColor = white

    override fun onGenerated(palette: Palette?) {
        extractColors(palette)
        setColors()
    }

    private fun extractColors(palette: Palette?) {
        val vibrant = palette!!.vibrantSwatch
        val dominant = palette.dominantSwatch
        bgColor = vibrant?.rgb ?: black
        textColor = vibrant?.bodyTextColor ?: white
        dominant?.let {
            if (it.population > 2000) {
                bgColor = it.rgb
                textColor = vibrant?.rgb ?: white
            }
        }
        if (similarColors(bgColor, textColor)) {
            if (bgColor == dominant?.rgb) {
                textColor = if (closerToBlack(bgColor)) white else black
            } else {
                bgColor = if (closerToBlack(textColor)) white else black
            }
        }
    }

    private fun setColors() {
        activity.setBackgroundColor(bgColor)
        activity.setTextColor(textColor)
    }

    private fun colorDistance(first: Int, second: Int): Double {
        val squareDistance = Math.pow(Color.red(first) - Color.red(second).toDouble(), 2.0) +
                Math.pow(Color.green(first) - Color.green(second).toDouble(), 2.0) +
                Math.pow(Color.blue(first) - Color.blue(second).toDouble(), 2.0)
        return Math.sqrt(squareDistance)
    }

    private fun similarColors(first: Int, second: Int): Boolean {
        return colorDistance(first, second) < 50
    }

    private fun closerToBlack(color: Int): Boolean {
        return colorDistance(color, Color.BLACK) < 220
    }

}

interface DynamicColorsActivity {
    fun setTextColor(color: Int)
    fun setBackgroundColor(color: Int)
}