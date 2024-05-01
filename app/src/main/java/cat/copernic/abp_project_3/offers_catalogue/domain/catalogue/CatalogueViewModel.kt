package cat.copernic.abp_project_3.offers_catalogue.domain.catalogue

import android.util.Log
import androidx.lifecycle.viewModelScope
import cat.copernic.abp_project_3.application.domain.ApplicationViewModel
import cat.copernic.abp_project_3.application.data.model.Category
import cat.copernic.abp_project_3.application.data.model.Offer
import cat.copernic.abp_project_3.application.data.service.category.CategoryService
import cat.copernic.abp_project_3.application.data.service.offer.OfferService
import cat.copernic.abp_project_3.application.data.service.review.ReviewService
import com.google.firebase.firestore.GeoPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * ViewModel for managing data related to the catalogue screen.
 *
 * This ViewModel provides functionality to fetch and manage offers, categories,
 * and filtering/sorting options for the catalogue screen.
 *
 * @param offerService The service responsible for fetching offers.
 * @param categoryService The service responsible for fetching categories.
 * @param reviewService The service responsible for fetching reviews related to offers.
 */
@HiltViewModel
class CatalogueViewModel @Inject constructor(
    private val offerService: OfferService,
    private val categoryService: CategoryService,
    private val reviewService: ReviewService,
) : ApplicationViewModel() {
    
    private var _catalogueDataState =
        MutableStateFlow(
            CatalogueDataState(sortList = listOf("", "", ""))
        )
    val catalogueDataState = _catalogueDataState.asStateFlow()

    /**
     * Updates the selected category option in the catalogue data state.
     *
     * @param category The selected category. Pass `null` to clear the selection.
     */
    fun changeSelectedOption(category: Category?) {
        _catalogueDataState.update {
            _catalogueDataState.value.onChangeSelectedOption(category)
        }
    }

    /**
     * Updates the search text in the catalogue data state.
     *
     * @param text The new search text.
     */
    fun onChangeSearchText(text: String): Unit {
        _catalogueDataState.update {
            _catalogueDataState.value.onChangeSearchText(text)
        }
    }

    /**
     * Updates the list of sort options in the catalogue data state.
     *
     * @param list The new list of sort options.
     */
    fun onChangeSortList(list: List<String>) {
        Log.d("test","is changing list")
        _catalogueDataState.update {
            _catalogueDataState.value.onChangeSortList(list)
        }
    }

    /**
     * Updates the selected range (distance filter) in the catalogue data state.
     *
     * @param range The selected range. Pass `null` to clear the range.
     */
    fun onChangeRange(range: ClosedFloatingPointRange<Float>?) {
        _catalogueDataState.update {
            _catalogueDataState.value.onChangeSelectedRange(range)
        }
    }

    /**
     * Updates the category dialog in the catalogue data state.
     *
     * @param category The category dialog to update.
     */
    fun onChangeCategoryDialog(category: Category) {
        _catalogueDataState.update {
            _catalogueDataState.value.onChangeCategoryDialog(category)
        }
    }

    /**
     * Updates the current location in the catalogue data state.
     *
     * @param geoPoint The new current location.
     */
    fun onChangeCurrentLocation(geoPoint: GeoPoint?): Unit {
        _catalogueDataState.update {
            _catalogueDataState.value.onChangeCurrentLocation(geoPoint)
        }
    }

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories = _categories.asStateFlow()

    private val _offers = MutableStateFlow<List<Offer>>(emptyList())

    val offers = combine(
        _offers,
        _catalogueDataState
    ) { allOffers, cData ->
        var filteredOffers = allOffers.filter { offer ->

            val matchesCategory = offer.categoryRef.contains(
                cData.selectedOption?.id ?: ""
            ) || cData.selectedOption == null
            val matchesSearch =
                offer.myItem.lowercase()
                    .contains(cData.searchText.lowercase()) || cData.searchText.isBlank()
            val matchesStars = when (cData.filterOption) {
                0 -> offer.averageStars in 0.0..0.9
                1 -> offer.averageStars in 1.0..1.9
                2 -> offer.averageStars in 2.0..2.9
                3 -> offer.averageStars in 3.0..3.9
                4 -> offer.averageStars in 4.0..4.9
                5 -> offer.averageStars >= 5.0
                else -> true
            }
            matchesCategory && matchesSearch && matchesStars

        }
        cData.currentLocation?.let {location ->
            cData.selectedRange?.let { range ->
                Log.d("filter location", "${cData.selectedRange}")
                filteredOffers = filteredOffers.filter {
                    val distance = distanceBetween(
                        it.location.latitude, it.location.longitude,
                        location.latitude, location.longitude
                    )
                    range.contains(distance)
                }.sortedBy {
                    distanceBetween(
                        it.location.latitude, it.location.longitude,
                        location.latitude, location.longitude
                    )
                }
            }
        }

        when (cData.sortOption) {
            0 -> filteredOffers.sortedByDescending { it.date }
            1 -> filteredOffers.sortedByDescending { it.averageStars }
            2 -> filteredOffers.sortedBy {
                cData.currentLocation?.let { location ->
                    distanceBetween(
                        it.location.latitude,
                        it.location.longitude,
                        location.latitude,
                        location.longitude
                    )
                }

            }

            else -> filteredOffers
        }

    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        _offers.value
    )

    init {
        getOffers()
        getCategories()
    }

    /**
     * Fetches the offers and combines them with reviews to calculate average stars for each offer.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getOffers() {
        launchCatching {
            combine(
                offerService.actualOffers,
                offerService.actualOffers.flatMapLatest { offers ->
                    combine(offers.map { offer ->
                        reviewService.getActualOfferReviews(offer.id)
                    }) { reviews ->
                        reviews
                    }
                }
            ) { offers, reviews ->
                offers.mapIndexed { index, offer ->
                    val offerReviews = reviews[index]
                    val totalReviews = offerReviews.size
                    val totalStars = offerReviews.sumOf { it.stars }
                    val averageStars =
                        if (totalReviews > 0) totalStars.toDouble() / totalReviews else 0.0
                    offer.copy(averageStars = averageStars)
                }
            }.collect { updatedOffers ->
                _offers.value = updatedOffers
            }
        }
    }

    /**
     * Fetches the list of categories from the category service.
     */
    private fun getCategories() {
        launchCatching {
            _categories.value = categoryService.getCategories()
        }
    }

    /**
     *  Update sortOption of catalogueDataState
     *
     * @param newOption Index of sort option
     */
    fun onChangeSortOption(newOption: Int?) {
        _catalogueDataState.update {
            _catalogueDataState.value.onChangeSortOption(newOption)
        }

    }

    /**
     * Calculates the distance between two geographical points using the Haversine formula.
     *
     * @param lat1 Latitude of the first point.
     * @param lon1 Longitude of the first point.
     * @param lat2 Latitude of the second point.
     * @param lon2 Longitude of the second point.
     * @return The distance between the two points in kilometers.
     */
    private fun distanceBetween(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val radioTierra = 6371.0 // Radius of Earth in km

        // Convert grades into radians
        val latitud1Rad = Math.toRadians(lat1)
        val longitud1Rad = Math.toRadians(lon1)
        val latitud2Rad = Math.toRadians(lat2)
        val longitud2Rad = Math.toRadians(lon2)


        val latDiff = latitud2Rad - latitud1Rad
        val lonDiff = longitud2Rad - longitud1Rad

        // Formula of Haversine
        val a = sin(latDiff / 2).pow(2) +
                cos(latitud1Rad) * cos(latitud2Rad) *
                sin(lonDiff / 2).pow(2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))


        return radioTierra * c

    }

    /**
     * @param newOption Index of filter star
     */
    fun changeFilterOption(newOption: Int?) {
        _catalogueDataState.update {
            _catalogueDataState.value.onChangeFilterOption(newOption)
        }
    }

}