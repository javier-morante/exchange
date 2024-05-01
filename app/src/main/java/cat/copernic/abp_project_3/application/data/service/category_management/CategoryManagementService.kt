package cat.copernic.abp_project_3.application.data.service.category_management

import android.net.Uri
import cat.copernic.abp_project_3.application.data.model.Category

/**
 * Interface that contains all the methods related to category management
 */
interface CategoryManagementService {

    /**
     * @param categoryData Category Instance
     * @param imageUri Category image Uri
     */
    suspend fun createCategory(categoryData: Category, imageUri: Uri?)

    /**
     * @param categoryData Category Instance
     * @param imageUri Category Image Uri
     */
    suspend fun updateCategory(categoryData: Category, imageUri: Uri?)

    /**
     * @param categoryData Category Instance
     */
    suspend fun deleteCategory(categoryData: Category)

}