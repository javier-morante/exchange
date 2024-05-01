package cat.copernic.abp_project_3.application.data.providers

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.GeoPoint

/**
 * Helper class for manage location-related operations, includes location permission checker and a retriever that retrieve
 */
object LocationProvider {

    /**
     * Checks if the app has been granted both fine and coarse location permissions.
     *
     * @param context The context used to check permissions.
     * @return `true` if both fine and coarse location permissions are granted; otherwise, `false`.
     */
    fun checkLocationPermissions(context: Context): Boolean {
        val fineLocationPermission = ActivityCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val coarseLocationPermission = ActivityCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        return fineLocationPermission && coarseLocationPermission
    }

    /**
     * Retrieves the device's last known location if location permissions are granted.
     *
     * @param context The context used to retrieve the location.
     * @param onSuccess Callback function triggered on successful location retrieval.
     *                  Receives a GeoPoint object containing the latitude and longitude.
     */
    @SuppressLint("MissingPermission")
    fun getActualLocation(
        context: Context,
        onSuccess: (GeoPoint) -> Unit,
    ) {
        val locationProvider = LocationServices.getFusedLocationProviderClient(context)

        if(checkLocationPermissions(context)) {
            locationProvider.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    Log.d(ContentValues.TAG, "New User Location: Latitude => ${it.latitude}, Longitude => ${it.longitude}")
                    onSuccess(GeoPoint(
                        it.latitude, it.longitude
                    ))
                }
            }
        }
    }

}