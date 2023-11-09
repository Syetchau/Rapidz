package io.rapidz.lib

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources

object RapidzImageUtils {

    fun getResizedDrawable(context: Context, @DrawableRes drawableRes: Int, @DimenRes heightRes: Int): Drawable? {
        val height = context.resources.getDimensionPixelOffset(heightRes)
        return AppCompatResources.getDrawable(context, drawableRes)?.resize(context, height)
    }

    fun Drawable.resize(context: Context, heightPx: Int): Drawable? {
        val bitmap = resize(heightPx) ?: return null
        return BitmapDrawable(context.resources, bitmap)
    }

    fun Drawable.resize(heightPx: Int): Bitmap? {
        return when (this) {
            is BitmapDrawable -> {
                bitmap.resize(heightPx)
            }

            is VectorDrawable -> {
                val ratio = intrinsicHeight.toFloat() / intrinsicWidth
                val widthPx = (heightPx / ratio).toInt()
                Bitmap.createBitmap(widthPx, heightPx, Bitmap.Config.ARGB_8888).apply {
                    val canvas = Canvas(this)
                    setBounds(0, 0, canvas.width, canvas.height)
                    draw(canvas)
                }
            }

            else -> return null
        }
    }

    fun Bitmap.resize(finalHeight: Int): Bitmap? {
        val ratio = height.toFloat() / width
        //LogUtils.d("ratio", ratio)
        if (ratio == 1f) {
            return this
        }

        if (height == finalHeight) {
            return this
        }

        val finalWidth = (finalHeight / ratio).toInt()
        return resize(finalWidth, finalHeight)
    }

    fun Bitmap.resize(width: Int, height: Int): Bitmap? = try {
        Bitmap.createScaledBitmap(this, width, height, true)
    } catch (e: Throwable) {
        //LogUtils.e(e)
        e.printStackTrace()
        null
    }
}