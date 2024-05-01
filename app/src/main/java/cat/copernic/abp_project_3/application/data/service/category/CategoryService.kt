package cat.copernic.abp_project_3.application.data.service.category

import cat.copernic.abp_project_3.application.data.model.Category
import kotlinx.coroutines.flow.Flow

/**
 * Interface that contains all the methods related to category
 */
interface CategoryService {

    val actualCategoriesList: Flow<List<Category>>

    /**
     * @param newCategory New category Instance
     */
    suspend fun createCategory(newCategory: Category)

    /**
     * @param id Category Id
     * @return Category Instance
     */
    suspend fun getCategory(id: String): Category?

    /**
     * @return List containing all the categories
     */
    suspend fun getCategories(): List<Category>

    /**
     * @param category Category Instance
     */
    suspend fun updateCategory(category: Category)

    /**
     * @param id Category Id
     */
    suspend fun deleteCategory(id: String)

}