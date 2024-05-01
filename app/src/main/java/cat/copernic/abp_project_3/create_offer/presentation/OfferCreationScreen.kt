package cat.copernic.abp_project_3.create_offer.presentation

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cat.copernic.abp_project_3.R
import cat.copernic.abp_project_3.application.presentation.components.button.ConfirmButtonComp
import cat.copernic.abp_project_3.application.presentation.components.divider.CustomDivider
import cat.copernic.abp_project_3.application.presentation.components.dropdown.OfferCategoryDropdown
import cat.copernic.abp_project_3.application.presentation.components.label.CustomTitle
import cat.copernic.abp_project_3.application.presentation.components.permision_handlers.RequestLocationPermission
import cat.copernic.abp_project_3.application.presentation.components.resource_picker.ImagePicker
import cat.copernic.abp_project_3.application.presentation.components.resource_picker.MapLocationPicker
import cat.copernic.abp_project_3.application.presentation.components.text_field.CustomLongTextField
import cat.copernic.abp_project_3.application.presentation.components.text_field.CustomTextField
import cat.copernic.abp_project_3.create_offer.domain.OfferCreationViewModel
import cat.copernic.abp_project_3.application.data.providers.LocationProvider
import cat.copernic.abp_project_3.application.presentation.AppBaseNav

/**
 * Composable function representing the screen for creating a new offer.
 * This screen allows users to input details of a new offer including image, trade item, desired item, description,
 * category, and location. The user's location permission is requested before displaying the creation interface.
 *
 * @param offerCreationViewModel The view model responsible for managing offer creation state and operations.
 * @param displayNotification     A function to display notifications or toasts with the provided message.
 * @param handleBackNavigation    A callback function to handle navigation when the user wishes to navigate back.
 */
@Composable
fun OfferCreationScreen(
    offerCreationViewModel: OfferCreationViewModel = hiltViewModel(),
    displayNotification: (String) -> Unit,
    handleNavigation: (String) -> Unit,
    handleBackNavigation: () -> Unit,
) {
    val context: Context = LocalContext.current
    val offerCreationState by offerCreationViewModel.offerCreationState.collectAsState()

    var permissionsReady by rememberSaveable { mutableStateOf(false) }

    RequestLocationPermission(
        onPermissionDenied = {
            handleBackNavigation()
        },
        onPermissionReady = {
            LocationProvider.getActualLocation(
                context = context,
                onSuccess = { location ->
                    offerCreationViewModel.setOfferLocation(location)
                }
            )
            permissionsReady = true
        }
    )

    if(permissionsReady) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.verticalScroll(
                    rememberScrollState()
                )
            ) {

                CustomTitle(
                    title = stringResource(R.string.offer_creation_title)
                )
                
                Spacer(modifier = Modifier.height(16.dp))

                // Image Picker Field
                ImagePicker(
                    title = stringResource(R.string.account_profile_settings_image_picker_label),
                    profileImageUrl = offerCreationState.offer.imageUrl,
                    handleImageChange = { offerCreationViewModel.setOfferImageUri(it) }
                )

                CustomDivider()

                // Trade Item text Field
                CustomTextField(
                    title = stringResource(R.string.offer_details_my_item_label),
                    value = offerCreationState.offer.myItem,
                    onValueChange = { offerCreationViewModel.setTradeItem(it) }
                )

                CustomDivider()

                // Provided Item text Field
                CustomTextField(
                    title = stringResource(R.string.offer_details_desired_item_label),
                    value = offerCreationState.offer.desiredItem,
                    onValueChange = { offerCreationViewModel.setProvidedItem(it) }
                )


                CustomDivider()

                // Description Field
                CustomLongTextField(
                    title = stringResource(R.string.offer_details_description_label),
                    value = offerCreationState.offer.description,
                    onValueChange = { offerCreationViewModel.setDescriptionItem(it) }
                )

                CustomDivider()

                // Category Dropdown Field
                OfferCategoryDropdown(
                    categories = offerCreationState.categories,
                    title = stringResource(R.string.offer_details_category_ref_label),
                    value = offerCreationState.selectedCategory.title,
                    onValueChange = { offerCreationViewModel.setSelectedCategory(it) }
                )

                CustomDivider()

                // Location Picker Map
                MapLocationPicker(
                    modifier = Modifier
                        .fillMaxWidth(),
                    title = stringResource(R.string.offer_creation_location_picker_label),
                    selectedLocation = offerCreationState.offer.location,
                    displayUserLocation = false,
                    handleLocationChange = { offerCreationViewModel.setOfferLocation(it) }
                )

                Spacer(modifier = Modifier.height(48.dp))
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                // Submit Button
                ConfirmButtonComp(
                    modifier = Modifier
                        .fillMaxWidth()
                    ,
                    title = stringResource(R.string.offer_creation_create_button_label),
                    onClick = {
                        if(offerCreationState.authUserStatus) {
                            offerCreationViewModel.handleCreateOffer(
                                handleNavigation = handleNavigation,
                                displayToast = displayNotification,
                            )

                        } else {
                            displayNotification("Sign in for creating offers")
                        }
                    }
                )
            }

        }
    }
}