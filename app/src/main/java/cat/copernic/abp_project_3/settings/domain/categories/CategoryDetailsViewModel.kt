package cat.copernic.abp_project_3.settings.domain.categories

import android.net.Uri
import cat.copernic.abp_project_3.application.domain.ApplicationViewModel
import cat.copernic.abp_project_3.application.data.model.Category
import cat.copernic.abp_project_3.application.data.service.category.CategoryService
import cat.copernic.abp_project_3.application.data.service.category_management.CategoryManagementService
import cat.copernic.abp_project_3.settings.presentation.categories.category_details.CategoryDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


/**
 * Hilt ViewModel that contains all the logic referent to the Category Details Screen, also
 * includes a state attribute and the correspondent methods for updating the category data
 * and deleting the category
 *
 * @property categoryManagementService Injected category management Service
 * @property categoryService Injected category Service
 */
@HiltViewModel
class CategoryDetailsViewModel @Inject constructor(
    private val categoryManagementService: CategoryManagementService,
    private val categoryService: CategoryService
): ApplicationViewModel() {

    private var _categoryDetailsState = MutableStateFlow(CategoryDetailsState())
    val categoryDetailsState get() = _categoryDetailsState.asStateFlow()

    fun setTitle(newTitle: String) {
        _categoryDetailsState.update { currentState ->
            val categoryData = currentState.categoryData.setTitle(newTitle)
            currentState.setCategoryData(categoryData)
        }
    }

    fun setDescription(newDescription: String) {
        _categoryDetailsState.update { currentState ->
            val categoryData = currentState.categoryData.setDescription(newDescription)
            currentState.setCategoryData(categoryData)
        }
    }

    fun setCategoryImage(newCategoryImage: Uri?) {
        _categoryDetailsState.update { currentState ->
            currentState.setCategoryImage(newCategoryImage)
        }
    }


    fun getCategoryData(categoryId: String) {
        launchCatching {
            _categoryDetailsState.update {currentState ->
                currentState.setCategoryData(
                    categoryService.getCategory(categoryId) ?: Category()
                )
            }
        }
    }


    fun handleUpdateCategoryData(
        displayToast: (String) -> Unit
    ) {
        launchCatching(
            displayToast
        ) {
            _categoryDetailsState.value.validate()
            categoryManagementService.updateCategory(
                _categoryDetailsState.value.categoryData,
                _categoryDetailsState.value.categoryImage
            )
            displayToast("Category updated Successfully")
        }
    }

    fun handleDeleteCategory(
        displayToast: (String) -> Unit,
        handleBackNavigation: () -> Unit
    ) {
        launchCatching(
            displayToast
        ) {
            categoryManagementService.deleteCategory(
                _categoryDetailsState.value.categoryData
            )
            displayToast("Category Deleted Successfully")
            handleBackNavigation()
        }

    }
}