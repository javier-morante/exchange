package cat.copernic.abp_project_3.settings.domain.offers

import androidx.compose.runtime.mutableStateListOf
import cat.copernic.abp_project_3.application.domain.ApplicationViewModel
import cat.copernic.abp_project_3.application.data.enums.UserRole
import cat.copernic.abp_project_3.application.data.model.Offer
import cat.copernic.abp_project_3.application.data.service.authentication.AuthenticationService
import cat.copernic.abp_project_3.application.data.service.offer.OfferService
import cat.copernic.abp_project_3.application.data.service.profile.ProfileService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Hilt ViewModel that contains all the logic referent to the Offers Management Screen,
 * also contains a state attribute and a method for reloading all the offers that belong to the
 * authenticated user account
 *
 * @property profileService Injected profile Service
 * @property authenticationService Injected authentication service
 * @property offerService Injected offer Service
 */
@HiltViewModel
class OffersManagementViewModel @Inject constructor(
    private val profileService: ProfileService,
    private val authenticationService: AuthenticationService,
    private val offerService: OfferService
): ApplicationViewModel() {

    private var _offersList = mutableStateListOf<Offer>()
    val offersList get() = _offersList.toList()

    init {
        reloadAuthUserOffers()
    }

    private fun reloadAuthUserOffers() {
        launchCatching {
            val profile = profileService.getProfile(authenticationService.currentAuthUserUid ?: "")

            if(profile != null && profile.role == UserRole.ADMINISTRATOR) {
                offerService.allOffers.collect { allOffers ->
                    _offersList.clear()
                    _offersList.addAll(allOffers)
                }
            } else {
                offerService.actualAuthUserOffers.collect { actualAuthUserOffers ->
                    _offersList.clear()
                    _offersList.addAll(actualAuthUserOffers)
                }
            }
        }
    }


}