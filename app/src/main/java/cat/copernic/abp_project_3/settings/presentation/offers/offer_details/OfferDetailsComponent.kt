package cat.copernic.abp_project_3.settings.presentation.offers.offer_details

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cat.copernic.abp_project_3.R
import cat.copernic.abp_project_3.application.presentation.components.button.ConfirmButtonComp
import cat.copernic.abp_project_3.application.presentation.components.button.DangerButtonComp
import cat.copernic.abp_project_3.application.presentation.components.divider.CustomDivider
import cat.copernic.abp_project_3.application.presentation.components.dropdown.OfferCategoryDropdown
import cat.copernic.abp_project_3.application.presentation.components.dropdown.OfferStateDropdown
import cat.copernic.abp_project_3.application.presentation.components.resource_picker.ImagePicker
import cat.copernic.abp_project_3.application.presentation.components.resource_picker.MapLocationPicker
import cat.copernic.abp_project_3.application.presentation.components.text_field.CustomLongTextField
import cat.copernic.abp_project_3.application.presentation.components.text_field.CustomTextField
import cat.copernic.abp_project_3.settings.domain.offers.OfferDetailsViewModel

/**
 * Component that contains all the ui elements referent to the Offer Details Screen
 *
 * @param offerDetailsViewModel
 * @param displayNotification
 * @param handleBackNavigation
 * @param offerProfileId
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OfferDetailsComponent(
    offerDetailsViewModel: OfferDetailsViewModel = hiltViewModel(),
    displayNotification: (String) -> Unit,
    handleBackNavigation: () -> Unit,
    offerProfileId: String?
) {
    val offerDetailsState by offerDetailsViewModel.offerDetailsState.collectAsState()

    LaunchedEffect(key1 = offerProfileId) {
        if(offerProfileId != null) {
            offerDetailsViewModel.getOfferData(offerProfileId)
        } else {
            handleBackNavigation()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                ),
                navigationIcon = {
                    IconButton(
                        onClick = handleBackNavigation
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = stringResource(R.string.back_button_helper)
                        )
                    }
                },
                title = {
                    Text(
                        style = MaterialTheme.typography.titleLarge,
                        text = stringResource(R.string.offer_management_title)
                    )
                }
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.verticalScroll(
                    rememberScrollState()
                )
            ) {

                // Image Picker Field
                ImagePicker(
                    title = stringResource(R.string.account_profile_settings_image_picker_label),
                    profileImageUrl = offerDetailsState.offer.imageUrl,
                    handleImageChange = { offerDetailsViewModel.setOfferImageUri(it) }
                )

                CustomDivider()

                // Trade Item text Field
                CustomTextField(
                    title = stringResource(R.string.offer_details_my_item_label),
                    value = offerDetailsState.offer.myItem,
                    onValueChange = { offerDetailsViewModel.setTradeItem(it) }
                )

                CustomDivider()

                // Provided Item text Field
                CustomTextField(
                    title = stringResource(R.string.offer_details_desired_item_label),
                    value = offerDetailsState.offer.desiredItem,
                    onValueChange = { offerDetailsViewModel.setProvidedItem(it) }
                )


                CustomDivider()

                // Description Field
                CustomLongTextField(
                    title = stringResource(R.string.offer_details_description_label),
                    value = offerDetailsState.offer.description,
                    onValueChange = { offerDetailsViewModel.setDescriptionItem(it) }
                )

                CustomDivider()

                // Category Dropdown Field
                OfferCategoryDropdown(
                    categories = offerDetailsState.categories,
                    title = stringResource(R.string.offer_details_category_ref_label),
                    value = offerDetailsState.selectedCategory.title,
                    onValueChange = { offerDetailsViewModel.setSelectedCategory(it) }
                )

                CustomDivider()

                // Offer State Dropdown Field
                OfferStateDropdown(
                    title = stringResource(R.string.offer_details_offer_state_label),
                    value = offerDetailsState.offer.offerState.title,
                    onValueChange = { offerDetailsViewModel.setOfferStateItem(it) }
                )

                CustomDivider()
                
                MapLocationPicker(
                    modifier = Modifier
                        .fillMaxWidth(),
                    title = stringResource(R.string.offer_creation_location_picker_label),
                    selectedLocation = offerDetailsState.offer.location,
                    displayUserLocation = true,
                    handleLocationChange = {
                        offerDetailsViewModel.setOfferLocation(it)
                    }
                )

                Spacer(modifier = Modifier.height(100.dp))
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                // Submit Button
                ConfirmButtonComp(
                    modifier = Modifier
                        .fillMaxWidth()
                    ,
                    title = stringResource(R.string.offer_details_save_offer_label),
                    onClick = {
                        offerDetailsViewModel.handleUpdateOffer(
                            displayToast = displayNotification,
                        )
                    }
                )

                DangerButtonComp(
                    modifier = Modifier
                        .fillMaxWidth(),
                    title = stringResource(R.string.offer_details_delete_offer_label),
                    onClick = {
                        offerDetailsViewModel.handleDeleteOffer(
                            displayToast = displayNotification,
                            handleBackNavigation = handleBackNavigation
                        )
                    }
                )
            }

        }

    }
}