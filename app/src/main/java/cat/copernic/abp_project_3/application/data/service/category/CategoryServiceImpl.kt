package cat.copernic.abp_project_3.application.data.service.category

import android.util.Log
import cat.copernic.abp_project_3.application.data.exceptions.services.CategoryException
import cat.copernic.abp_project_3.application.data.model.Category
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named

/**
 *
 *
 * @property categoryCollection
 */
class CategoryServiceImpl @Inject constructor(
    @Named("categoryCollection") private val categoryCollection: CollectionReference
): CategoryService {

    /**
     * Returns a flow that contains all the categories
     */
    override val actualCategoriesList: Flow<List<Category>>
        get() = callbackFlow {
            val listener = categoryCollection
                .orderBy("title")
                .addSnapshotListener { snapshot, error ->
                    if(error != null) {
                        return@addSnapshotListener
                    }

                    if(snapshot != null) {
                        Log.d("category:actual_categories", "-----> Fetching Categories <-----")

                        val categories = snapshot.documents.map { document ->
                            document.toObject(Category::class.java) ?: Category()
                        }

                        trySend(categories)
                    }
                }

            awaitClose { listener.remove() }
        }

    /**
     * Method uncharged in creating a new category in the database
     *
     * @param newCategory
     */
    override suspend fun createCategory(newCategory: Category) {
        categoryCollection
            .document(newCategory.id)
            .set(newCategory)
            .addOnFailureListener {
                throw CategoryException("Error creating category")
            }.await()
    }

    /**
     * Method uncharged in getting a category from the database
     *
     * @param id
     * @return
     */
    override suspend fun getCategory(id: String): Category? {
        return categoryCollection
            .document(id)
            .get()
            .addOnFailureListener {
                throw CategoryException("Error getting category")
            }.await().toObject(Category::class.java)
    }

    /**
     * Method that returns a list with all the categories from the database
     *
     * @return
     */
    override suspend fun getCategories(): List<Category> {
        val catColl = categoryCollection
            .orderBy("title")
            .get()
            .addOnFailureListener {
                //throw CategoryException("Error getting categories")
            }.await().toObjects(Category::class.java)

        for(cat in catColl) {
            Log.d("category:actual_categories", "Category => $cat")
        }

        return catColl
    }

    /**
     * Method that updates a category from the database
     *
     * @param category
     */
    override suspend fun updateCategory(category: Category) {
        categoryCollection
            .document(category.id)
            .set(category)
            .addOnFailureListener {
                throw CategoryException("Error updating categories")
            }.await()
    }

    /**
     * Method that deletes a category from the database
     *
     * @param id
     */
    override suspend fun deleteCategory(id: String) {
        categoryCollection
            .document(id)
            .delete()
            .addOnFailureListener {
                throw CategoryException("Error deleting categories")
            }.await()
    }

}