package cat.copernic.abp_project_3.application.data.module

import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

/**
 * The following class is a dependency injection module from the Dagger-Hilt library
 *
 * The @Module annotation indicates that the class is a dagger module, that is a class that
 * provides dependencies to Dagger
 *
 * The @InstallIn(SingletonComponent::class) annotation specifies that the provided dependencies
 * by this module have to be reachable by the scope of the singleton component, this means
 * that the provided instances by this module will exist during all the application lifecycle
 *
 * Then inside of the class we can find all the provider methods properly annotated with the
 * following annotations:
 *
 * The @Provides annotation indicates that the following method provides a dependency, so Dagger
 * will utilize this method for obtaining a instance from the provided dependency
 *
 * The @Singleton annotation ensures that the provided instance by the method is unique and
 * reused each time that the dependency is needed
 *
 * And some of the methods are annotated with the @Name annotation this is because more than one
 * method is providing the same output type, so we have to define a specific name for being able to
 * differentiate between multiple instances of the same type
 *
 */
@Module
@InstallIn(SingletonComponent::class)
class FirestoreModule {

    /**
     * @return Instance of firebase firestore
     */
    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return Firebase.firestore
    }



    // Collection providers -----------------------------------

    /**
     * @param firestore Injected instance of firestore
     * @return Instance of firestore profile collection reference
     */
    @Provides
    @Singleton
    @Named("profileCollection")
    fun provideProfileCollectionReference(firestore: FirebaseFirestore): CollectionReference {
        return firestore.collection(PROFILE_COLLECTION_NAME)
    }

    /**
     * @param firestore Injected instance of firestore
     * @return Instance of firestore category collection reference
     */
    @Provides
    @Singleton
    @Named("categoryCollection")
    fun provideCategoryCollectionReference(firestore: FirebaseFirestore): CollectionReference {
        return firestore.collection(CATEGORY_COLLECTION_NAME)
    }

    /**
     * @param firestore Injected instance of firestore
     * @return Instance of firestore conversation collection reference
     */
    @Provides
    @Singleton
    @Named("conversationCollection")
    fun provideConversationCollectionReference(firestore: FirebaseFirestore): CollectionReference {
        return firestore.collection(CONVERSATION_COLLECTION_NAME)
    }

    /**
     * @param firestore Injected instance of firestore
     * @return Instance of firestore message collection reference
     */
    @Provides
    @Singleton
    @Named("messageCollection")
    fun provideMessageCollectionReference(firestore: FirebaseFirestore): CollectionReference {
        return firestore.collection(MESSAGE_COLLECTION_NAME)
    }

    /**
     * @param firestore Injected instance of firestore
     * @return Instance of firestore offer collection reference
     */
    @Provides
    @Singleton
    @Named("offerCollection")
    fun provideOfferCollectionReference(firestore: FirebaseFirestore): CollectionReference {
        return firestore.collection(OFFER_COLLECTION_NAME)
    }

    /**
     * @param firestore Injected instance of firestore
     * @return Instance of firestore Review collection reference
     */
    @Provides
    @Singleton
    @Named("messageCollection")
    fun provideReviewCollectionReference(firestore: FirebaseFirestore): CollectionReference {
        return firestore.collection(REVIEW_COLLECTION_NAME)
    }


    /**
     * The following object defines the collection names constants
     */
    companion object {
        const val PROFILE_COLLECTION_NAME = "profiles"
        const val CATEGORY_COLLECTION_NAME = "categories"
        const val CONVERSATION_COLLECTION_NAME = "conversations"
        const val MESSAGE_COLLECTION_NAME = "messages"
        const val OFFER_COLLECTION_NAME = "offers"
        const val REVIEW_COLLECTION_NAME = "reviews"
    }

}