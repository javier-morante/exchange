package cat.copernic.abp_project_3.settings.domain.categories

import android.net.Uri
import cat.copernic.abp_project_3.application.domain.ApplicationViewModel
import cat.copernic.abp_project_3.application.data.service.category_management.CategoryManagementService
import cat.copernic.abp_project_3.settings.presentation.categories.create_category.CategoryCreationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * Hilt ViewModel that contains all the logic referent to the Category Creation Screen, also
 * contains a state attribute and methods for and the corresponding method for creating the
 * category
 *
 * @property categoryManagementService Injected category management Service
 */
@HiltViewModel
class CategoryCreationViewModel @Inject constructor(
    private val categoryManagementService: CategoryManagementService
): ApplicationViewModel() {

    // States ------------------------------

    private var _categoryCreationState = MutableStateFlow(CategoryCreationState())
    val categoryCreationState get() = _categoryCreationState.asStateFlow()

    fun setTitle(newTitle: String) {
        _categoryCreationState.update { currentState ->
            val categoryData = currentState.categoryData.setTitle(newTitle)
            currentState.setCategoryData(categoryData)
        }
    }

    fun setDescription(newDescription: String) {
        _categoryCreationState.update { currentState ->
            val categoryData = currentState.categoryData.setDescription(newDescription)
            currentState.setCategoryData(categoryData)
        }
    }

    fun setCategoryImage(newCategoryImage: Uri?) {
        _categoryCreationState.update { currentState ->
            currentState.setCategoryImage(newCategoryImage)
        }
    }



    // Methods ------------------------------

    fun handleCategoryCreation(
        displayToast: (String) -> Unit,
        handleBackNavigation: () -> Unit
    ) {
        launchCatching(
            displayToast
        ) {
            _categoryCreationState.value.validate()
            categoryManagementService.createCategory(
                _categoryCreationState.value.categoryData,
                _categoryCreationState.value.categoryImage
            )
            displayToast("Category created successfully")
            handleBackNavigation()
        }
    }

}