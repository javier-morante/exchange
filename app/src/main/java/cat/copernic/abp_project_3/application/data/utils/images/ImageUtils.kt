package cat.copernic.abp_project_3.application.data.utils.images

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat

/**
 * Utility class for image-related operations.
 */
object ImageUtils {

    /**
     * Generates a Bitmap from a drawable resource ID.
     *
     * @param context The context used to retrieve resources.
     * @param drawable The drawable resource ID.
     * @return A Bitmap representing the drawable resource.
     * @throws IllegalArgumentException if the drawable resource ID is invalid or cannot be loaded.
     */
    fun getBitmapFromImage(context: Context, drawable: Int): Bitmap {
        val drawable = ContextCompat.getDrawable(context, drawable)

        val bitmap = Bitmap.createBitmap(
            drawable!!.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)

        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        return bitmap
    }

}