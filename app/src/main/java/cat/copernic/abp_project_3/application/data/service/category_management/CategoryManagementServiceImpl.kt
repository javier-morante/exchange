package cat.copernic.abp_project_3.application.data.service.category_management

import android.net.Uri
import android.util.Log
import cat.copernic.abp_project_3.application.data.enums.StorageReferences
import cat.copernic.abp_project_3.application.data.exceptions.services.CategoryManagementException
import cat.copernic.abp_project_3.application.data.model.Category
import cat.copernic.abp_project_3.application.data.service.category.CategoryService
import cat.copernic.abp_project_3.application.data.service.offer.OfferService
import cat.copernic.abp_project_3.application.data.service.storage.StorageService
import javax.inject.Inject

/**
 * Service uncharged in managing all the categories and the category images actions
 *
 * @property categoryService
 * @property storageService
 * @property offerService
 */
class CategoryManagementServiceImpl @Inject constructor(
    private val categoryService: CategoryService,
    private val storageService: StorageService,
    private val offerService: OfferService
): CategoryManagementService {

    /**
     * Method that creates a category and stores the category image in storage
     *
     * @param categoryData
     * @param imageUri
     */
    override suspend fun createCategory(categoryData: Category, imageUri: Uri?) {
        if(imageUri != null) {
            try {
                categoryData.imageUrl = storageService.uploadImage(
                    StorageReferences.CATEGORIES_IMAGE,
                    categoryData.id,
                    imageUri
                )
            } catch (e: Exception) {
                Log.d("category_management:create_category", "Error creating category")
            }
        }

        categoryService.createCategory(categoryData)
    }

    /**
     * Method that updates the data and image from a category
     *
     * @param categoryData
     * @param imageUri
     */
    override suspend fun updateCategory(categoryData: Category, imageUri: Uri?) {
        if(imageUri != null) {
            try {
                categoryData.imageUrl = storageService.uploadImage(
                    storageReference = StorageReferences.ACCOUNTS_IMAGE,
                    imageName = categoryData.id,
                    imageUri = imageUri
                )
            } catch (e: Exception) {
                Log.d("category_management:update_category", "Error updating category")
            }
        }

        categoryService.updateCategory(categoryData)
    }

    /**
     * Method that deletes the data and image from a category
     *
     * @param categoryData
     */
    override suspend fun deleteCategory(categoryData: Category) {
        if(offerService.getOffersByCategory(categoryData).isNotEmpty()) {
           throw CategoryManagementException("Category can't be deleted, offers belong to that category")
        }

        try {
            storageService.deleteImage(
                storageReference = StorageReferences.CATEGORIES_IMAGE,
                imageName = categoryData.id
            )
        } catch (e: Exception) {
            Log.d("category_management:delete_category", "Error deleting category")
        }


        categoryService.deleteCategory(categoryData.id)
    }
}