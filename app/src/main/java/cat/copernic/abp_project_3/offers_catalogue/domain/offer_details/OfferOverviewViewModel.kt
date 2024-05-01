package cat.copernic.abp_project_3.offers_catalogue.domain.offer_details

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import cat.copernic.abp_project_3.application.domain.ApplicationViewModel
import cat.copernic.abp_project_3.application.data.model.Category
import cat.copernic.abp_project_3.application.data.model.Conversation
import cat.copernic.abp_project_3.application.data.model.Message
import cat.copernic.abp_project_3.application.data.model.Offer
import cat.copernic.abp_project_3.application.data.model.Profile
import cat.copernic.abp_project_3.application.data.model.Review
import cat.copernic.abp_project_3.application.data.service.authentication.AuthenticationService
import cat.copernic.abp_project_3.application.data.service.category.CategoryService
import cat.copernic.abp_project_3.application.data.service.conversations_management.ConversationsManagementService
import cat.copernic.abp_project_3.application.data.service.offer.OfferService
import cat.copernic.abp_project_3.application.data.service.profile.ProfileService
import cat.copernic.abp_project_3.application.data.service.review.ReviewService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * ViewModel class that manages offer-related data and interactions within the application.
 * This ViewModel integrates with various services to fetch and update data asynchronously,
 * exposing StateFlows for observing data changes.
 *
 * @param profileService         Service responsible for managing user profile data.
 * @param offerService           Service responsible for managing offer data.
 * @param reviewService          Service responsible for managing review data.
 * @param categoryService        Service responsible for managing category data.
 * @param authenticationService  Service responsible for managing user authentication status.
 * @param conversationService    Service responsible for managing conversations.
 * @param messageService         Service responsible for managing messages within conversations.
 */
@HiltViewModel
class OfferOverviewViewModel @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val profileService: ProfileService,
    private val offerService: OfferService,
    private val reviewService: ReviewService,
    private val categoryService: CategoryService,
    private val conversationsManagementService: ConversationsManagementService
) : ApplicationViewModel() {

    private var _authUserStatus: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val authUserStatus = _authUserStatus.asStateFlow()

    private var _authUserUid: MutableStateFlow<String> = MutableStateFlow(
        authenticationService.currentAuthUserUid ?: ""
    )
    val authUserUid = _authUserUid.asStateFlow()

    private var _offer: MutableStateFlow<Offer?> = MutableStateFlow(null)
    val offer = _offer.asStateFlow()

    private var _reviews = mutableStateListOf<Review>()
    val reviews get() = _reviews.toList()

    private var _user: MutableStateFlow<Profile?> = MutableStateFlow(null)
    val user = _user.asStateFlow()

    private var _currentUserProfile: MutableStateFlow<Profile?> = MutableStateFlow(null)
    val currentUserProfile = _currentUserProfile.asStateFlow()

    private var _currentOfferCategory: MutableStateFlow<Category?> = MutableStateFlow(null)
    val currentOfferCategory = _currentOfferCategory.asStateFlow()

    private var _currentOfferReviews = mutableStateListOf<Review>()
    val currentOfferReviews = _currentOfferReviews.toList()


    private val _reviewProfilePairs = mutableStateListOf<Pair<Review, Profile?>>()
    val reviewProfilePairs get() = _reviewProfilePairs.toList()

    init {
        launchCatching {
            authenticationService.authStatus.collect { status ->
                _authUserStatus.value = status
            }
        }
    }

    /**
     * Creates a new conversation between the current user and the owner of the selected offer,
     * along with an initial message expressing interest in the offer.
     */
    fun createNewConversation() {
        launchCatching {
            val newConversation = Conversation(
                usersRef = listOf(
                    _currentUserProfile.value?.id ?: "",
                    _offer.value?.ownerRef ?: ""
                )
            )

            val startMessage = Message(
                ownerRef = _currentUserProfile.value?.id ?: "",
                content = "Hi👋, I am ${_currentUserProfile.value?.name}, and i'm interested in the ${_offer.value?.myItem} offer"
            )

            conversationsManagementService.createConversation(
                newConversation = newConversation,
                startMessage = startMessage
            )

        }
    }

    /**
     * Sets the selected offer and fetches related details including owner profile,
     * category, and reviews for the offer with the specified UID.
     *
     * @param offerUid The UID of the selected offer.
     */
    fun setSelectedOffer(offerUid: String) {
        launchCatching {
            _offer.value = offerService.getOffer(offerUid)
            Log.d(TAG, "Offer => ${_offer.value}")

            _user.value = profileService.getProfile(_offer.value?.ownerRef ?: "")
            Log.d(TAG, "User Profile => ${_user.value}")

            _currentOfferCategory.value = categoryService.getCategory(_offer.value?.categoryRef ?: "")
            Log.d(TAG, "Offer Category => ${_currentOfferCategory.value}")

            reviewService.getActualOfferReviews(offerUid).collect { reviews ->
                val sortedReviews = reviews.sortedByDescending { it.date.toDate() }

                _reviews.clear()
                _reviews.addAll(sortedReviews)

                _reviewProfilePairs.clear()
                sortedReviews.forEach { review ->
                    val profile = profileService.getProfile(review.ownerRef)
                    _reviewProfilePairs.add(review to profile)
                }

                val totalReviews = sortedReviews.size
                val totalStars = sortedReviews.sumOf { it.stars }
                val averageStars = if (totalReviews > 0) totalStars.toDouble() / totalReviews else 0.0

                _offer.value?.let { offer ->
                    _offer.value = offer.copy(
                        averageStars = averageStars,
                        totalReviews = totalReviews,
                        totalStars = totalStars
                    )
                }

                Log.d(TAG, "Reviews => ${_reviews.toList()}")
                Log.d(TAG, "Review Profiles => ${_reviewProfilePairs.map { it.second }}")
            }
        }
    }

    /**
     * Fetches the profile of the current authenticated user and updates the state.
     */
    fun fetchCurrentUserProfile() {
        launchCatching {
            try {
                val currentAuthUserId = authenticationService.currentAuthUserUid
                if (currentAuthUserId != null) {
                    val profile = profileService.getProfile(currentAuthUserId)
                    _currentUserProfile.value = profile
                } else {
                    _currentUserProfile.value = null
                }
            } catch (e: Exception) {
                _currentUserProfile.value = null
            }
        }
    }

    /**
     * Fetches and updates the list of reviews for the specified offer ID.
     *
     * @param offerId The ID of the offer to fetch reviews for.
     */
    fun fetchActualReviews(offerId: String) {
        launchCatching {
            reviewService.getActualOfferReviews(offerId).collect { reviewsList ->
                Log.d(TAG, "Offer Reviews => $reviewsList")
                _currentOfferReviews.clear()
                _currentOfferReviews.addAll(reviewsList)
            }
        }
    }
}