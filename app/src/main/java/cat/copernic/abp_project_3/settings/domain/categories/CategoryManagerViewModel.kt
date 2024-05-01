package cat.copernic.abp_project_3.settings.domain.categories

import androidx.compose.runtime.mutableStateListOf
import cat.copernic.abp_project_3.application.domain.ApplicationViewModel
import cat.copernic.abp_project_3.application.data.model.Category
import cat.copernic.abp_project_3.application.data.service.category.CategoryService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Hilt ViewModel that contains all the logic referent to the Category Manager Screen,
 * also contains a list with all the actual categories and a method for reloading those categories
 *
 * @property categoryService Injected category Service
 */
@HiltViewModel
class CategoryManagerViewModel @Inject constructor(
    private val categoryService: CategoryService
): ApplicationViewModel() {

    // States ------------------------------
    private var _categoryList = mutableStateListOf<Category>()
    val categoryList get() = _categoryList.toList()

    init {
        reloadCategories()
    }

    // Methods ------------------------------

    private fun reloadCategories() {

        launchCatching {
            categoryService.actualCategoriesList.collect { categoriesList ->
                _categoryList.clear()
                _categoryList.addAll(categoriesList)
            }
        }
    }
}