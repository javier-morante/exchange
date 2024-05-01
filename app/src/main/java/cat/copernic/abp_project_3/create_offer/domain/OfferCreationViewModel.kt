package cat.copernic.abp_project_3.create_offer.domain

import android.net.Uri
import cat.copernic.abp_project_3.application.domain.ApplicationViewModel
import cat.copernic.abp_project_3.create_offer.presentation.OfferCreationState
import cat.copernic.abp_project_3.application.data.model.Category
import cat.copernic.abp_project_3.application.data.service.authentication.AuthenticationService
import cat.copernic.abp_project_3.application.data.service.category.CategoryService
import cat.copernic.abp_project_3.application.data.service.offer_management.OfferManagementService
import cat.copernic.abp_project_3.application.presentation.AppBaseNav
import com.google.firebase.firestore.GeoPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * ViewModel responsible for managing the state and actions related to offer creation.
 *
 * This ViewModel handles the creation of offers by interacting with authentication,
 * category, and offer management services.
 *
 * @param authenticationService The service responsible for authentication operations.
 * @param categoryService The service responsible for category-related operations.
 * @param offerManagementService The service responsible for offer management operations.
 */
@HiltViewModel
class OfferCreationViewModel @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val categoryService: CategoryService,
    private val offerManagementService: OfferManagementService
): ApplicationViewModel() {

    private var _offerCreationState = MutableStateFlow(OfferCreationState())
    val offerCreationState get() = _offerCreationState.asStateFlow()

    /**
     * Sets the trade item (user's item) for the offer being created.
     *
     * @param newTradeItem The trade item to set.
     */
    fun setTradeItem(newTradeItem: String) {
        _offerCreationState.update { currentState ->
            val offerData = currentState.offer.setMyItem(newTradeItem)
            currentState.setOfferData(offerData)
        }
    }

    /**
     * Sets the provided item (desired item) for the offer being created.
     *
     * @param newProvidedItem The provided item to set.
     */
    fun setProvidedItem(newProvidedItem: String) {
        _offerCreationState.update { currentState ->
            val offerData = currentState.offer.setDesiredItem(newProvidedItem)
            currentState.setOfferData(offerData)
        }
    }

    /**
     * Sets the description for the offer being created.
     *
     * @param newDescription The description to set.
     */
    fun setDescriptionItem(newDescription: String) {
        _offerCreationState.update { currentState ->
            val offerData = currentState.offer.setDescription(newDescription)
            currentState.setOfferData(offerData)
        }
    }

    /**
     * Sets the location for the offer being created.
     *
     * @param newOfferLocation The location (GeoPoint) to set.
     */
    fun setOfferLocation(newOfferLocation: GeoPoint) {
        _offerCreationState.update { currentState ->
            val offerData = currentState.offer.setLocation(newOfferLocation)
            currentState.setOfferData(offerData)
        }
    }

    /**
     * Sets the image URI for the offer being created.
     *
     * @param newOfferImageUri The image URI to set.
     */
    fun setOfferImageUri(newOfferImageUri: Uri?) {
        _offerCreationState.update { currentState ->
            currentState.setOfferImageUri(newOfferImageUri)
        }
    }

    /**
     * Initializes the ViewModel by fetching the categories and collecting authentication user status.
     */
    init {
        fetchCategories()
        collectAuthUserStatus()
    }

    /**
     * Fetches the list of categories from the category service and updates the state.
     * Additionally, selects the first category if available.
     */
    private fun fetchCategories() {
        launchCatching {
            _offerCreationState.update { currentState ->
                currentState.setCategories(
                    categoryService.getCategories()
                )
            }

            if(_offerCreationState.value.categories.isNotEmpty()) {
                setSelectedCategory(_offerCreationState.value.categories[0])
            }
        }
    }

    /**
     * Sets the selected category in the offer creation state.
     *
     * @param category The category to set as the selected category.
     */
    fun setSelectedCategory(category: Category) {
        _offerCreationState.value.offer.categoryRef = category.id
        _offerCreationState.update { currentState ->
            currentState.setSelectedCategory(category)
        }
    }

    /**
     * Collects the authentication user status and updates the state accordingly.
     */
    private fun collectAuthUserStatus() {
        launchCatching {
            authenticationService.authStatus.collect { status ->
                _offerCreationState.update { currentState ->
                    currentState.setAuthUserStatus(status)
                }
            }
        }
    }

    /**
     * Handles the creation of the offer, validating the state and invoking the offer management service.
     *
     * @param displayToast A function to display a toast message.
     */
    fun handleCreateOffer(
        handleNavigation: (String) -> Unit,
        displayToast: (String) -> Unit,
    ) {
        launchCatching(
            displayToast
        ) {
            _offerCreationState.value.validate()

            offerManagementService.createOffer(
                _offerCreationState.value.offer,
                _offerCreationState.value.offerImageUri
            )

            displayToast("Offer Created successfully")
            _offerCreationState.value = OfferCreationState()
            handleNavigation(AppBaseNav.CATALOGUE.name)
        }
    }

}