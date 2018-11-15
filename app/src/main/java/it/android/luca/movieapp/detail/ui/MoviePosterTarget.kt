package it.android.luca.movieapp.detail.ui

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.v7.graphics.Palette
import android.widget.ImageView
import com.bumptech.glide.request.target.ImageViewTarget
import kotlinx.android.synthetic.main.activity_detail.*

class MoviePosterTarget(val poster: ImageView, val activity: DetailActivity) : ImageViewTarget<Drawable>(poster) {
    override fun setResource(resource: android.graphics.drawable.Drawable?) {
        resource?.let {
            setImage(it)
            extractColor(it)
        }
    }

    private fun setImage(resource: android.graphics.drawable.Drawable) {
        poster.setBackgroundDrawable(resource)
    }

    private fun extractColor(resource: android.graphics.drawable.Drawable) {
        val b = (resource.current as BitmapDrawable).bitmap

        Palette.from(b).clearFilters().generate { palette ->
            val black = Color.BLACK
            val white = Color.WHITE
            val vibrant = palette!!.vibrantSwatch
            val dominant = palette.dominantSwatch
            var bgColor = vibrant?.rgb ?: black
            var textColor = vibrant?.bodyTextColor ?: white
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
            activity.toolbar.navigationIcon?.setColorFilter(textColor, PorterDuff.Mode.SRC_ATOP)
            activity.collapsing_toolbar.setCollapsedTitleTextColor(textColor)
            activity.collapsing_toolbar.setContentScrimColor(bgColor)

        }

    }

    private fun colorDistance(first: Int, second: Int): Double {
        val squareDistance = Math.pow(Color.red(first) - Color.red(second).toDouble(), 2.0) +
                Math.pow(Color.green(first) - Color.green(second).toDouble(), 2.0) +
                Math.pow(Color.blue(first) - Color.blue(second).toDouble(), 2.0)
        return Math.sqrt(squareDistance)
    }

    private fun similarColors(first: Int, second: Int): Boolean{
        return colorDistance(first, second) < 50
    }

    private fun closerToBlack(color: Int): Boolean {
        return colorDistance(color, Color.BLACK) < 220
    }
}