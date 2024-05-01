package cat.copernic.abp_project_3.settings.presentation.categories

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cat.copernic.abp_project_3.application.data.model.Category
import cat.copernic.abp_project_3.settings.domain.categories.CategoryManagerViewModel
import cat.copernic.abp_project_3.settings.presentation.categories.category_details.CategoryDetailsComponent
import cat.copernic.abp_project_3.settings.presentation.categories.category_list.CategoryListComponent
import cat.copernic.abp_project_3.settings.presentation.categories.create_category.CategoryCreationComponent

/**
 * Enum class that contains all the navigation references to the Category Manager Screen
 *
 */
enum class CategoryManagerBaseNav {
    LIST,
    DETAILS,
    CREATE
}

/**
 * Hoisted component that contains the viewModel injection an a reference to the
 * CategoryManagerScreenUI
 *
 * @param categoryManagerViewModel
 * @param displayNotification
 * @param navController
 * @param backNavigation
 */
@Composable
fun CategoryManagerScreen(
    categoryManagerViewModel: CategoryManagerViewModel = hiltViewModel(),
    displayNotification: (String) -> Unit,
    navController: NavHostController = rememberNavController(),
    backNavigation: () -> Unit
) {
    CategoryManagerScreenUI(
        navController = navController,
        displayNotification = displayNotification,
        handleMenuBackNavigation = backNavigation,
        categoryList = categoryManagerViewModel.categoryList,
    )
}

/**
 * Component that contains a nav controller with all the references for each one of the
 * screens available inside of the Category Manager
 *
 * @param navController
 * @param displayNotification
 * @param handleMenuBackNavigation
 * @param categoryList
 */
@Composable
fun CategoryManagerScreenUI(
    navController: NavHostController = rememberNavController(),
    displayNotification: (String) -> Unit,
    handleMenuBackNavigation: () -> Unit = {},
    categoryList: List<Category> = emptyList(),
) {
    NavHost(
        navController = navController,
        startDestination = CategoryManagerBaseNav.LIST.name
    ) {
        // Category List Screen
        composable(CategoryManagerBaseNav.LIST.name) {
            CategoryListComponent(
                handleMenuBackNavigation = handleMenuBackNavigation,
                handleCategoryCreationNavigation = {
                    navController.navigate(CategoryManagerBaseNav.CREATE.name)
                },
                categoryListComponent = categoryList,
                handleDetails = { categoryId ->
                    navController.navigate(
                        "${CategoryManagerBaseNav.DETAILS.name}/${categoryId}"
                    )
                }
            )
        }
        // Category Details Screen
        composable(
            route = "${CategoryManagerBaseNav.DETAILS.name}/{categoryId}",
            arguments = listOf(navArgument("categoryId") { defaultValue = "" })
        ){ backStackEntry ->
            CategoryDetailsComponent(
                displayNotification = displayNotification,
                handleBackNavigation = { navController.popBackStack() },
                categoryId = backStackEntry.arguments?.getString("categoryId"),
            )
        }
        // Create Category Screen
        composable(CategoryManagerBaseNav.CREATE.name) {
            CategoryCreationComponent(
                displayNotification = displayNotification,
                handleBackNavigation = { navController.popBackStack() },
            )
        }
    }
}