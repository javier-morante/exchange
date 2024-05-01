package cat.copernic.abp_project_3.application.presentation.components.screen_lock

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext

/**
 * Composable function for locking the screen orientation of the current activity.
 *
 * @param orientation The desired screen orientation to lock (e.g., ActivityInfo.SCREEN_ORIENTATION_PORTRAIT).
 */
@Composable
fun LockScreenOrientation(orientation: Int) {
    val context = LocalContext.current
    DisposableEffect(Unit) {
        val activity = context.findActivity() ?: return@DisposableEffect onDispose {}
        val originalOrientation = activity.requestedOrientation
        activity.requestedOrientation = orientation
        onDispose {
            // restore original orientation when view disappears
            activity.requestedOrientation = originalOrientation
        }
    }
}

/**
 * Extension function to recursively find the hosting Activity from a given Context.
 *
 * @return The hosting Activity if found, or null if no Activity is found in the Context hierarchy.
 */
fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}