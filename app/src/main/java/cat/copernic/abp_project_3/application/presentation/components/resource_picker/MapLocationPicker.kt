package cat.copernic.abp_project_3.application.presentation.components.resource_picker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import cat.copernic.abp_project_3.R
import cat.copernic.abp_project_3.application.data.providers.LocationProvider
import cat.copernic.abp_project_3.application.data.utils.images.ImageUtils
import cat.copernic.abp_project_3.application.presentation.components.button.CustomIconButton
import cat.copernic.abp_project_3.application.presentation.components.button.StylizedNavigationButtonComponent
import cat.copernic.abp_project_3.application.presentation.components.permision_handlers.RequestLocationPermission
import com.google.firebase.firestore.GeoPoint
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotation
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.locationcomponent.location

/**
 * Composable screen that shows a map of Mapbox that you can pick the location
 *
 * @param modifier The style modifier
 * @param title The title that shows on the screen
 * @param selectedLocation The option selected by the user
 * @param handleLocationChange Callback to change de selected option
 */
@OptIn(MapboxExperimental::class)
@Composable
fun MapLocationPicker(
    modifier: Modifier = Modifier,
    title: String,
    selectedLocation: GeoPoint,
    displayUserLocation: Boolean,
    handleLocationChange: (GeoPoint) -> Unit
) {

    val context = LocalContext.current

    var permissionsReady by rememberSaveable { mutableStateOf(false) }

    val locationIconBitmap = ImageUtils.getBitmapFromImage(
        context, R.drawable.location
    )

    val mapViewportState = rememberMapViewportState {
        setCameraOptions {
            center(Point.fromLngLat(0.0, 0.0))
            zoom(2.0)
            pitch(0.0)
        }
    }

    var dialogStatus by rememberSaveable { mutableStateOf(false) }
    val updateDialogStatus = { newStatus: Boolean ->
        mapViewportState.flyTo(
            cameraOptions = cameraOptions {
                center(Point.fromLngLat(
                    selectedLocation.longitude,
                    selectedLocation.latitude
                ))
                zoom(14.0)
            },
            animationOptions = MapAnimationOptions.mapAnimationOptions {
                duration(5)
            }
        )
        dialogStatus = newStatus
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        StylizedNavigationButtonComponent(
            title = title,
            leadingIcon = painterResource(R.drawable.location_on),
            onClick = {
                updateDialogStatus(true)
            }
        )
    }

    // Picker dialog
    if(dialogStatus) {

        RequestLocationPermission(
            onPermissionDenied = {
                updateDialogStatus(false)
            },
            onPermissionReady = {
                permissionsReady = true
            }
        )

        if(permissionsReady) {

            Dialog(
                onDismissRequest = {
                    updateDialogStatus(false)
                }
            ) {
                Card(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(500.dp)
                            .height(800.dp)
                            .clip(MaterialTheme.shapes.medium)
                            .padding(16.dp)
                    ) {

                        MapboxMap(
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.medium)
                                .fillMaxSize()
                                .padding(16.dp),
                            mapViewportState = mapViewportState,
                            onMapClickListener = { point ->
                                handleLocationChange(
                                    GeoPoint(
                                        point.latitude(),
                                        point.longitude()
                                    )
                                )
                                true
                            }
                        ) {

                            if(displayUserLocation) {
                                MapEffect(Unit) { mapView ->
                                    mapView.location.apply {
                                        enabled = true
                                    }
                                }
                            }

                            PointAnnotation(
                                point = Point.fromLngLat(
                                    selectedLocation.longitude,
                                    selectedLocation.latitude
                                ),
                                iconImageBitmap = locationIconBitmap
                            )
                        }


                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(16.dp)
                        ) {

                            CustomIconButton(
                                modifier = Modifier
                                    .padding(16.dp),
                                icon = painterResource(R.drawable.my_location),
                                description = stringResource(R.string.restore_location_button_helper),
                                onClick = {
                                    LocationProvider.getActualLocation(
                                        context,
                                        handleLocationChange
                                    )
                                }
                            )

                            CustomIconButton(
                                modifier = Modifier
                                    .padding(16.dp),
                                icon = painterResource(R.drawable.done),
                                description = stringResource(R.string.accept_location_button_helper),
                                onClick = {
                                    updateDialogStatus(false)
                                }
                            )

                        }
                    }
                }
            }
        }
    }
}