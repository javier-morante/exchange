package cat.copernic.abp_project_3.settings.domain.offers

import android.net.Uri
import cat.copernic.abp_project_3.application.domain.ApplicationViewModel
import cat.copernic.abp_project_3.application.data.enums.OfferState
import cat.copernic.abp_project_3.application.data.model.Category
import cat.copernic.abp_project_3.application.data.model.Offer
import cat.copernic.abp_project_3.application.data.service.category.CategoryService
import cat.copernic.abp_project_3.application.data.service.offer.OfferService
import cat.copernic.abp_project_3.application.data.service.offer_management.OfferManagementService
import cat.copernic.abp_project_3.settings.presentation.offers.offer_details.OfferDetailsState
import com.google.firebase.firestore.GeoPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * Hilt ViewModel that contains all the logic referent to the Offer Details Screen, it contains
 * a state attribute and all the necessary methods for updating, deleting and gathering
 * offers data
 *
 * @property categoryService Injected category Service
 * @property offerService Injected offer Service
 * @property offerManagementService Injected offer management Service
 */
@HiltViewModel
class OfferDetailsViewModel @Inject constructor(
    private val categoryService: CategoryService,
    private val offerService: OfferService,
    private val offerManagementService: OfferManagementService
): ApplicationViewModel() {

    private var _offerDetailsState = MutableStateFlow(OfferDetailsState())
    val offerDetailsState get() = _offerDetailsState.asStateFlow()

    fun setTradeItem(newTradeItem: String) {
        _offerDetailsState.update { currentState ->
            val offerData = currentState.offer.setMyItem(newTradeItem)
            currentState.setOfferData(offerData)
        }
    }

    fun setProvidedItem(newProvidedItem: String) {
        _offerDetailsState.update { currentState ->
            val offerData = currentState.offer.setDesiredItem(newProvidedItem)
            currentState.setOfferData(offerData)
        }
    }

    fun setDescriptionItem(newDescription: String) {
        _offerDetailsState.update { currentState ->
            val offerData = currentState.offer.setDescription(newDescription)
            currentState.setOfferData(offerData)
        }
    }

    private fun setCategoryRefItem(newCategoryRef: String) {
        _offerDetailsState.update { currentState ->
            val offerData = currentState.offer.setCategoryRef(newCategoryRef)
            currentState.setOfferData(offerData)
        }
    }

    fun setOfferStateItem(newOfferState: OfferState) {
        _offerDetailsState.update { currentState ->
            val offerData = currentState.offer.setOfferState(newOfferState)
            currentState.setOfferData(offerData)
        }
    }

    fun setOfferImageUri(newOfferImageUri: Uri?) {
        _offerDetailsState.update { currentState ->
            currentState.setOfferImageUri(newOfferImageUri)
        }
    }

    fun setSelectedCategory(newSelectedCategory: Category) {
        _offerDetailsState.update { currentState ->
            setCategoryRefItem(newSelectedCategory.id)
            currentState.setSelectedCategory(newSelectedCategory)
        }
    }

    fun setOfferLocation(newOfferLocation: GeoPoint) {
        _offerDetailsState.update { currentState ->
            val offerData = currentState.offer.setLocation(newOfferLocation)
            currentState.setOfferData(offerData)
        }
    }



    init {
        launchCatching {
            _offerDetailsState.update { currentState ->
                currentState.setCategories(
                    categoryService.getCategories()
                )
            }
        }
    }


    fun getOfferData(offerId: String) {
        launchCatching {
            val offerData = offerService.getOffer(offerId) ?: Offer()
            _offerDetailsState.update { currentState ->
                currentState.setOfferData(offerData)
            }

            setSelectedCategory(
                categoryService.getCategory(offerData.categoryRef) ?: Category()
            )
        }
    }

    fun handleUpdateOffer(
        displayToast: (String) -> Unit,
    ) {
        launchCatching(
            displayToast
        ) {
            _offerDetailsState.value.validate()
            offerManagementService.updateOffer(
                _offerDetailsState.value.offer,
                _offerDetailsState.value.offerImageUri
            )
            displayToast("Offer Updated successfully")
        }
    }

    fun handleDeleteOffer(
        displayToast: (String) -> Unit,
        handleBackNavigation: () -> Unit
    ) {
        launchCatching(
            displayToast
        ) {
            offerManagementService.deleteOffer(
                _offerDetailsState.value.offer
            )
            displayToast("Offer Deleted successfully")
            handleBackNavigation()
        }
    }

}