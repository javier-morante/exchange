package cat.copernic.abp_project_3.offers_catalogue.domain.catalogue

import cat.copernic.abp_project_3.application.data.model.Category
import com.google.firebase.firestore.GeoPoint

/**
 * Represents the state data for managing catalogue-related information.
 *
 * This data class holds various properties that define the state of the catalogue screen,
 * such as the selected category option, search text, sort options, location, and filtering parameters.
 *
 * @param selectedOption The selected category option.
 * @param searchText The text used for filtering offers.
 * @param categoryDialog The category dialog state.
 * @param sortList The list of available sort options.
 * @param sortOption The currently selected sort option.
 * @param currentLocation The current user location.
 * @param selectedRange The selected distance range for filtering offers by location.
 * @param filterOption The currently selected filter option.
 */
data class CatalogueDataState(
    var selectedOption: Category? = null,
    var searchText: String = "",
    var categoryDialog: Category = Category(),
    var sortList: List<String> = emptyList(),
    var sortOption: Int? = null,
    var currentLocation: GeoPoint? = null,
    val selectedRange: ClosedFloatingPointRange<Float>? = null,
    var filterOption: Int? = null
) {

    /**
     * Creates a new state with the selected category option updated.
     *
     * @param category The new selected category option.
     * @return A new instance of CatalogueDataState with the updated selected category option.
     */
    fun onChangeSelectedOption(category: Category?): CatalogueDataState {
        return this.copy(selectedOption = category)
    }

    /**
     * Creates a new state with the sort list updated.
     *
     * @param list The new list of sort options.
     * @return A new instance of CatalogueDataState with the updated sort list.
     */
    fun onChangeSortList(list: List<String>): CatalogueDataState {
        return this.copy(sortList = list)
    }

    /**
     * Creates a new state with the search text updated.
     *
     * @param str The new search text.
     * @return A new instance of CatalogueDataState with the updated search text.
     */
    fun onChangeSearchText(str: String): CatalogueDataState {
        return this.copy(searchText = str)
    }

    /**
     * Creates a new state with the sort option updated.
     *
     * @param num The new sort option.
     * @return A new instance of CatalogueDataState with the updated sort option.
     */
    fun onChangeSortOption(num: Int?): CatalogueDataState {
        return this.copy(sortOption = num)
    }

    /**
     * Creates a new state with the filter option updated.
     *
     * @param num The new filter option.
     * @return A new instance of CatalogueDataState with the updated filter option.
     */
    fun onChangeFilterOption(num: Int?): CatalogueDataState {
        return this.copy(filterOption = num)
    }

    /**
     * Creates a new state with the category dialog updated.
     *
     * @param category The new category dialog state.
     * @return A new instance of CatalogueDataState with the updated category dialog state.
     */
    fun onChangeCategoryDialog(category: Category): CatalogueDataState {
        return this.copy(categoryDialog = category)
    }

    /**
     * Creates a new state with the current location updated.
     *
     * @param geoPoint The new current location.
     * @return A new instance of CatalogueDataState with the updated current location.
     */
    fun onChangeCurrentLocation(geoPoint: GeoPoint?): CatalogueDataState {
        return this.copy(currentLocation = geoPoint)
    }

    /**
     * Creates a new state with the selected range updated.
     *
     * @param range The new selected distance range.
     * @return A new instance of CatalogueDataState with the updated selected range.
     */
    fun onChangeSelectedRange(range: ClosedFloatingPointRange<Float>?): CatalogueDataState {
        return this.copy(selectedRange = range)
    }
}
