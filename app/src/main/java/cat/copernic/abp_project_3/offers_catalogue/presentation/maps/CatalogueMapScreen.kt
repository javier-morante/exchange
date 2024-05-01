package cat.copernic.abp_project_3.offers_catalogue.presentation.maps

import android.graphics.Color
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cat.copernic.abp_project_3.R
import cat.copernic.abp_project_3.application.presentation.components.button.ConfirmButtonComp
import cat.copernic.abp_project_3.application.presentation.components.button.CustomIconButton
import cat.copernic.abp_project_3.application.presentation.components.divider.CustomDivider
import cat.copernic.abp_project_3.application.presentation.components.label.CustomTitle
import cat.copernic.abp_project_3.application.presentation.components.permision_handlers.RequestLocationPermission
import cat.copernic.abp_project_3.application.data.dto.OfferIdDTO
import cat.copernic.abp_project_3.application.data.model.Offer
import cat.copernic.abp_project_3.application.data.utils.images.ImageUtils
import cat.copernic.abp_project_3.offers_catalogue.domain.catalogue.CatalogueViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotationGroup
import com.mapbox.maps.extension.style.expressions.dsl.generated.literal
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.annotation.AnnotationConfig
import com.mapbox.maps.plugin.annotation.AnnotationSourceOptions
import com.mapbox.maps.plugin.annotation.ClusterOptions
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location

/**
 * Composable function representing a screen that displays a map with annotations for offers.
 *
 * This screen integrates with MapboxMap to show offers as point annotations on the map. Users
 * can interact with the map to view offer details and navigate back.
 *
 * @param catalogueViewModel The view model used to manage data for this screen, typically injected
 *                           using Hilt's view model delegate.
 * @param handleOfferNavigation Callback function to handle navigation to a specific offer detail screen
 *                              based on the offer ID.
 * @param handleBackNavigation Callback function to handle navigation back from this screen.
 */
@OptIn(MapboxExperimental::class, ExperimentalMaterial3Api::class)
@Composable
fun CatalogueMapScreen(
    catalogueViewModel: CatalogueViewModel = hiltViewModel(),
    handleOfferNavigation: (String) -> Unit,
    handleBackNavigation: () -> Unit
) {
    val context = LocalContext.current

    val offers by catalogueViewModel.offers.collectAsState()

    var selectedOffer by remember { mutableStateOf<Offer?>(null) }

    val locationIconBitmap = ImageUtils.getBitmapFromImage(
        context, R.drawable.location
    )

    val gson = Gson()

    val mapViewportState = rememberMapViewportState {
        setCameraOptions {
            center(Point.fromLngLat(0.0, 0.0))
            zoom(4.0)
            pitch(0.0)
        }
    }

    var permissionsReady by rememberSaveable { mutableStateOf(false) }

    RequestLocationPermission(
        onPermissionDenied = {
            handleBackNavigation()
        },
        onPermissionReady = {
            permissionsReady = true
        }
    )

    if(permissionsReady) {
        Scaffold { innerPadding ->
            Box(
                modifier = Modifier.padding(innerPadding)
            ) {
                MapboxMap(
                    modifier = Modifier.fillMaxSize(),
                    mapViewportState = mapViewportState,
                ) {

                    MapEffect(Unit) { mapView ->
                        mapView.location.apply {
                            enabled = true
                            locationPuck = createDefault2DPuck(withBearing = true)
                            puckBearingEnabled = true
                            puckBearing = PuckBearing.HEADING
                        }
                        mapViewportState.transitionToFollowPuckState()
                    }

                    PointAnnotationGroup(
                        annotations = offers.map {
                            PointAnnotationOptions()
                                .withData(JsonParser.parseString("{ offerId:${it.id} }"))
                                .withPoint(Point.fromLngLat(
                                    it.location.longitude,
                                    it.location.latitude
                                ))
                                .withIconImage(locationIconBitmap)
                            //.withCircleRadius(20.0)
                            //.withCircleColor(Color.GREEN)
                        },
                        annotationConfig = AnnotationConfig(
                            annotationSourceOptions = AnnotationSourceOptions(
                                clusterOptions = ClusterOptions(
                                    textColorExpression = Expression.color(Color.YELLOW),
                                    textColor = Color.BLACK, // Will not be applied as textColorExpression has been set
                                    textSize = 20.0,
                                    circleRadiusExpression = literal(25.0),
                                    colorLevels = listOf(
                                        Pair(100, Color.RED),
                                        Pair(50, Color.BLUE),
                                        Pair(0, Color.GREEN)
                                    )
                                )
                            )
                        ),
                        onClick = {
                            val offerId = gson.fromJson(it.getData(), OfferIdDTO::class.java)
                            selectedOffer = offers.first{ offer -> offer.id == offerId.offerId }
                            mapViewportState.flyTo(
                                cameraOptions = cameraOptions {
                                    center(Point.fromLngLat(
                                        it.geometry.longitude(),
                                        it.geometry.latitude()
                                    ))
                                    zoom(16.0)
                                },
                                animationOptions = MapAnimationOptions.mapAnimationOptions {
                                    duration(5)
                                }
                            )
                            true
                        }
                    )
                }

                CustomIconButton(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp),
                    icon = painterResource(R.drawable.my_location),
                    description = stringResource(R.string.restore_location_button_helper),
                    onClick = {
                        mapViewportState.transitionToFollowPuckState()
                    }
                )

                CustomIconButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                    icon = painterResource(R.drawable.grid_view),
                    description = stringResource(R.string.switch_offers_grid_button),
                    onClick = handleBackNavigation
                )
            }
        }

        if(selectedOffer != null) {
            ModalBottomSheet(
                onDismissRequest = {
                    selectedOffer = null
                },
                sheetState = rememberModalBottomSheetState()
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(horizontal = 32.dp)
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(MaterialTheme.shapes.medium)
                                .border(
                                    width = 2.dp,
                                    color = MaterialTheme.colorScheme.secondary,
                                    shape = MaterialTheme.shapes.medium
                                ),
                            model = ImageRequest.Builder(context)
                                .data(selectedOffer!!.imageUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            placeholder = painterResource(R.drawable.ic_launcher_background),
                            contentScale = ContentScale.Crop
                        )

                        Column {
                            CustomTitle(
                                title = selectedOffer!!.myItem
                            )

                            CustomDivider(space = 4.dp)

                            Text(
                                modifier = Modifier.padding(8.dp),
                                text = selectedOffer!!.description
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(64.dp))


                    ConfirmButtonComp(
                        modifier = Modifier.fillMaxWidth(),
                        title = stringResource(R.string.offer_details_map_button),
                        onClick = {
                            handleOfferNavigation(selectedOffer!!.id)
                        }
                    )

                    Spacer(modifier = Modifier.height(64.dp))
                }
                }
            }
        }
    }