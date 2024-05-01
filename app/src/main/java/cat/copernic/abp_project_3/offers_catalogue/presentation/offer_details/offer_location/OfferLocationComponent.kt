package cat.copernic.abp_project_3.offers_catalogue.presentation.offer_details.offer_location

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import cat.copernic.abp_project_3.R
import cat.copernic.abp_project_3.application.data.model.Offer
import cat.copernic.abp_project_3.application.data.utils.images.ImageUtils
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
 * Composable function representing the location component of an offer displayed on a map.
 *
 * This function displays a Mapbox map centered on the location of a specific offer, with an icon
 * representing the offer's location.
 *
 * @param offer The offer object containing information about the location to be displayed on the map.
 */
@OptIn(MapboxExperimental::class)
@Composable
fun OfferLocationComponent(
    offer: Offer
) {
    val context = LocalContext.current

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

    MapboxMap(
        modifier = Modifier
            .fillMaxSize(),
        mapViewportState = mapViewportState
    ) {
        MapEffect(Unit) { mapView ->
            mapView.location.apply {
                enabled = true
            }
        }

        PointAnnotation(
            point = Point.fromLngLat(
                offer.location.longitude,
                offer.location.latitude
            ),
            iconImageBitmap = locationIconBitmap
        )

        mapViewportState.flyTo(
            cameraOptions = cameraOptions {
                center(Point.fromLngLat(
                    offer.location.longitude,
                    offer.location.latitude
                ))
                zoom(16.0)
            },
            animationOptions = MapAnimationOptions.mapAnimationOptions {
                duration(5)
            }
        )
    }
}