package cat.copernic.abp_project_3.offers_catalogue.presentation.catalogue

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import cat.copernic.abp_project_3.R
import cat.copernic.abp_project_3.application.presentation.components.button.CustomIconButton
import cat.copernic.abp_project_3.application.presentation.components.permision_handlers.RequestLocationPermission
import cat.copernic.abp_project_3.application.data.model.Category
import cat.copernic.abp_project_3.application.data.model.Offer
import cat.copernic.abp_project_3.application.data.providers.LocationProvider
import cat.copernic.abp_project_3.offers_catalogue.domain.catalogue.CatalogueViewModel
import cat.copernic.abp_project_3.offers_catalogue.presentation.OffersCatalogueBaseNav
import cat.copernic.abp_project_3.offers_catalogue.presentation.catalogue.components.ButtonRadioDialog
import cat.copernic.abp_project_3.offers_catalogue.presentation.catalogue.components.ButtonSliderDialog
import cat.copernic.abp_project_3.offers_catalogue.presentation.catalogue.components.CatalogueTopBar
import cat.copernic.abp_project_3.offers_catalogue.presentation.catalogue.components.ExpandFilters
import cat.copernic.abp_project_3.offers_catalogue.presentation.catalogue.components.ItemCard

/**
 * This method process view model and pass the data and methods processed to CatalogueScreen view
 *
 * @param catalogueViewModel The view model of the catalogue screen
 * @param navController The controller to navigate between screens
 * @param handleMapNavigation Method to navigate into map
 */
@Composable
fun CatalogueScreen(
    catalogueViewModel: CatalogueViewModel = hiltViewModel(),
    navController: NavHostController,
    handleMapNavigation: () -> Unit
) {

    val context = LocalContext.current

    var isLocationPermission by rememberSaveable {
        mutableStateOf(LocationProvider.checkLocationPermissions(context))
    }

    isLocationPermission = LocationProvider.checkLocationPermissions(context)

    val offers by catalogueViewModel.offers.collectAsState()
    val categories by catalogueViewModel.categories.collectAsState()
    val catalogueData by catalogueViewModel.catalogueDataState.collectAsState()

    val byDate = stringResource(R.string.sort_by_date)
    val byReviews = stringResource(R.string.sort_by_reviews)
    val byDistance = stringResource(id = R.string.sort_by_distance)
    val star = stringResource(R.string.star)
    val stars = stringResource(R.string.stars)
    val list = mutableListOf(byDate, byReviews)
    if (isLocationPermission) {
        list.add(byDistance)
    }
    catalogueViewModel.onChangeSortList(list)

    LaunchedEffect(true) {
        LocationProvider.getActualLocation(context) {
            catalogueViewModel.onChangeCurrentLocation(it)
        }
    }

    val filterOptions = mutableListOf<String>().apply {
        addAll((0..5).map { index ->
            if (index == 1)
                "$index $star"
            else
                "$index $stars"
        })
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CatalogueScreen(
            offers = offers,
            categories = categories,
            selectedOption = catalogueData.selectedOption,
            selectedRange = catalogueData.selectedRange,
            categoryDialog = catalogueData.categoryDialog,
            sortOptions = catalogueData.sortList,
            selectedSortOption = catalogueData.sortOption,
            filterOptions = filterOptions,
            selectedFilterOption = catalogueData.filterOption,
            cardHandleClick = {
                navController.navigate(
                    "${OffersCatalogueBaseNav.DETAILS.name}/${offers[it].id}"
                )
            },
            onChangeOption = { catalogueViewModel.changeSelectedOption(it) },
            onChangeSearchText = { catalogueViewModel.onChangeSearchText(it) },
            onChangeCategoryDialog = { catalogueViewModel.onChangeCategoryDialog(it) },
            onChangeSortOption = { catalogueViewModel.onChangeSortOption(it) },
            onFilterOptionSelected = { catalogueViewModel.changeFilterOption(it) },
            onChangeRange = { catalogueViewModel.onChangeRange(it) },
            onChangeLocationPermission = { isLocationPermission = it }
        )

        CustomIconButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            icon = painterResource(R.drawable.map),
            description = stringResource(R.string.switch_map_button),
            onClick = handleMapNavigation
        )
    }
}

/**
 * Displays a catalogue screen with a list of offers and various filtering options and a floating button that navigate to a map
 *
 * @param offers List of offers to display in the catalogue.
 * @param categories List of available categories for filtering.
 * @param selectedOption Currently selected category option.
 * @param categoryDialog Current category dialog state.
 * @param selectedRange Selected range for distance filtering.
 * @param sortOptions List of sorting options.
 * @param selectedSortOption Index of the currently selected sorting option.
 * @param filterOptions List of filtering options.
 * @param selectedFilterOption Index of the currently selected filter option.
 * @param onChangeSearchText Callback for text search changes.
 * @param onChangeOption Callback for category selection changes.
 * @param cardHandleClick Callback for item card click events.
 * @param onChangeCategoryDialog Callback for category dialog changes.
 * @param onChangeSortOption Callback for sorting option changes.
 * @param onChangeRange Callback for range (distance) changes.
 * @param onFilterOptionSelected Callback for selected filter option changes.
 * @param onChangeLocationPermission Callback for location permission changes.
 */
@SuppressLint("ResourceType")
@Composable
fun CatalogueScreen(
    offers: List<Offer>,
    categories: List<Category>,
    selectedOption: Category?,
    categoryDialog: Category,
    selectedRange: ClosedFloatingPointRange<Float>?,
    sortOptions: List<String>,
    selectedSortOption: Int?,
    filterOptions: List<String>,
    selectedFilterOption: Int?,
    onChangeSearchText: (String) -> Unit = {},
    onChangeOption: (Category?) -> Unit = {},
    cardHandleClick: (Int) -> Unit = {},
    onChangeCategoryDialog: (Category) -> Unit = {},
    onChangeSortOption: (Int?) -> Unit = {},
    onChangeRange: (ClosedFloatingPointRange<Float>?) -> Unit = {},
    onFilterOptionSelected: (Int?) -> Unit = {},
    onChangeLocationPermission: (Boolean) -> Unit = {}

) {


    RequestLocationPermission(onPermissionDenied = { onChangeLocationPermission(false) },
        { onChangeLocationPermission(true) })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        CatalogueTopBar(
            onTextFieldTextChanged = onChangeSearchText,
            options = categories,
            selectedOption = selectedOption,
            onChangeCategory = onChangeOption,
            categoryDialog = categoryDialog,
            onChangeCategoryDialog = onChangeCategoryDialog,
        )

        ExpandFilters {
            ButtonRadioDialog(
                title = stringResource(id = R.string.sort),
                radioOptions = sortOptions,
                selectedOption = selectedSortOption,
                onOptionSelect = onChangeSortOption
            )
            Spacer(modifier = Modifier.width(10.dp))
            ButtonRadioDialog(
                title = stringResource(id = R.string.filter_by_stars),
                radioOptions = filterOptions,
                selectedOption = selectedFilterOption,
                onOptionSelect = onFilterOptionSelected
            )

            Spacer(modifier = Modifier.width(10.dp))
            ButtonSliderDialog(
                title = stringResource(id = R.string.filter_by_distance),
                minimumRange = 0f,
                maximumRange = 100f,
                selectedRange = selectedRange,
                onChangeRange = onChangeRange,
                onChangeLocationPermission = onChangeLocationPermission
            )
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxWidth(),
        ) {
            items(offers.size) { index ->
                ItemCard(
                    title = offers[index].myItem,
                    subTitle = offers[index].desiredItem,
                    averageStarsFormatted = "%.1f".format(offers[index].averageStars),
                    imagePath = offers[index].imageUrl,
                    handleOnClick = { cardHandleClick(index) }
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun CatalogueScreenPreview() {
    val listOffer = listOf(
        Offer(imageUrl = "https://comercialmoncho.com/12818-large_default/martillo-una-irimo-520-61-2.jpg"),
        Offer(),
        Offer()
    )
    val listCategory = listOf(Category(title = "hola"), Category(), Category())
    CatalogueScreen(
        offers = listOffer,
        categories = listCategory,
        selectedOption = listCategory[0],
        categoryDialog = Category(),
        sortOptions = listOf("a"),
        selectedSortOption = null,
        selectedRange = 0f..0f,
        selectedFilterOption = null,
        filterOptions = listOf(),
        onFilterOptionSelected = {}
    )

}

