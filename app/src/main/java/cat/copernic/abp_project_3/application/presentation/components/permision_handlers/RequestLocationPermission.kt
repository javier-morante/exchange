package cat.copernic.abp_project_3.application.presentation.components.permision_handlers

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import cat.copernic.abp_project_3.application.data.providers.LocationProvider

/**
 *  Composable Method to request user permissions about his location
 *
 * @param onPermissionDenied Callback Is used when the permissions request is denied
 * @param onPermissionReady Callback Is used when the permissions request is accepted
 */
@Composable
fun RequestLocationPermission(
    onPermissionDenied: () -> Unit,
    onPermissionReady: () -> Unit
) {
    val context = LocalContext.current

    val permissionRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        if(!permissionsMap.values.all { it }) {
            onPermissionDenied()
        } else {
            onPermissionReady()
        }
    }

    LaunchedEffect(Unit) {
        if(!LocationProvider.checkLocationPermissions(context)) {
            permissionRequest.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        } else {
            onPermissionReady()
        }
    }
}
