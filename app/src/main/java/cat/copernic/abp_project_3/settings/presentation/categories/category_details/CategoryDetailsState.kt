package cat.copernic.abp_project_3.settings.presentation.categories.category_details

import android.net.Uri
import cat.copernic.abp_project_3.application.data.model.Category

/**
 * Data class that contains all the state attributes referent to the Category Details Screen
 * and also a method that validates the entire class data
 *
 * @property categoryData
 * @property categoryImage
 */
data class CategoryDetailsState(
    var categoryData: Category = Category(),
    var categoryImage: Uri? = null
) {

    fun setCategoryData(newCategoryData: Category): CategoryDetailsState {
        return this.copy(categoryData = newCategoryData)
    }

    fun setCategoryImage(newCategoryImage: Uri?): CategoryDetailsState {
        return this.copy(categoryImage = newCategoryImage)
    }

    // Validators

    fun validate() {
        categoryData.validate()
    }
}