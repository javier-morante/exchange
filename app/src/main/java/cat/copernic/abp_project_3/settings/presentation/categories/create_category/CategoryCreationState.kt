package cat.copernic.abp_project_3.settings.presentation.categories.create_category

import android.net.Uri
import cat.copernic.abp_project_3.application.data.exceptions.validators.ValidationException
import cat.copernic.abp_project_3.application.data.model.Category

/**
 * Data class that stores the state attributes of the Category Creation Screen and
 * also a method that validates the entire class data
 *
 * @property categoryData
 * @property categoryImage
 */
data class CategoryCreationState(
    var categoryData: Category = Category(),
    var categoryImage: Uri? = null
) {

    fun setCategoryData(newCategoryData: Category): CategoryCreationState {
        return this.copy(categoryData = newCategoryData)
    }

    fun setCategoryImage(newCategoryImage: Uri?): CategoryCreationState {
        return this.copy(categoryImage = newCategoryImage)
    }

    // Validators

    fun validate() {
        if(categoryImage == null) {
            throw ValidationException("No category image selected")
        }

        categoryData.validate()
    }
}