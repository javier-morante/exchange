package cat.copernic.abp_project_3.application.data.module

import cat.copernic.abp_project_3.application.data.service.account.AccountService
import cat.copernic.abp_project_3.application.data.service.account.AccountServiceImpl
import cat.copernic.abp_project_3.application.data.service.authentication.AuthenticationService
import cat.copernic.abp_project_3.application.data.service.authentication.AuthenticationServiceImpl
import cat.copernic.abp_project_3.application.data.service.category.CategoryService
import cat.copernic.abp_project_3.application.data.service.category.CategoryServiceImpl
import cat.copernic.abp_project_3.application.data.service.category_management.CategoryManagementService
import cat.copernic.abp_project_3.application.data.service.category_management.CategoryManagementServiceImpl
import cat.copernic.abp_project_3.application.data.service.conversation.ConversationService
import cat.copernic.abp_project_3.application.data.service.conversation.ConversationServiceImpl
import cat.copernic.abp_project_3.application.data.service.conversations_management.ConversationsManagementService
import cat.copernic.abp_project_3.application.data.service.conversations_management.ConversationsManagementServiceImpl
import cat.copernic.abp_project_3.application.data.service.message.MessageService
import cat.copernic.abp_project_3.application.data.service.message.MessageServiceImpl
import cat.copernic.abp_project_3.application.data.service.offer.OfferService
import cat.copernic.abp_project_3.application.data.service.offer.OfferServiceImpl
import cat.copernic.abp_project_3.application.data.service.offer_management.OfferManagementService
import cat.copernic.abp_project_3.application.data.service.offer_management.OfferManagementServiceImpl
import cat.copernic.abp_project_3.application.data.service.profile.ProfileService
import cat.copernic.abp_project_3.application.data.service.profile.ProfileServiceImpl
import cat.copernic.abp_project_3.application.data.service.review.ReviewService
import cat.copernic.abp_project_3.application.data.service.review.ReviewServiceImpl
import cat.copernic.abp_project_3.application.data.service.storage.StorageService
import cat.copernic.abp_project_3.application.data.service.storage.StorageServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
 * The @Binds annotation indicates that the method implementation is linked with a interface
 * inside of the dagger scope, is more efficient than @Provides because it does not require
 * a call to a method, it just links the implementation to the interface automatically
 *
 * The @Singleton annotation ensures that the provided instance by the method is unique and
 * reused each time that the dependency is needed
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    /**
     * @param impl Authentication Service Implementation
     * @return Authentication Service Interface
     */
    @Binds
    @Singleton
    abstract fun provideAuthenticationService(
        impl: AuthenticationServiceImpl
    ): AuthenticationService

    /**
     * @param impl Category Service Implementation
     * @return Category Service Interface
     */
    @Binds
    @Singleton
    abstract fun provideCategoryService(
        impl: CategoryServiceImpl
    ): CategoryService

    /**
     * @param impl Conversation Service Implementation
     * @return Conversation Service Interface
     */
    @Binds
    @Singleton
    abstract fun provideConversationService(
        impl: ConversationServiceImpl
    ): ConversationService

    /**
     * @param impl Message Service Implementation
     * @return Message Service Interface
     */
    @Binds
    @Singleton
    abstract fun provideMessageService(
        impl: MessageServiceImpl
    ): MessageService

    /**
     * @param impl Offer Service Implementation
     * @return Offer Service Interface
     */
    @Binds
    @Singleton
    abstract fun provideOfferService(
        impl: OfferServiceImpl
    ): OfferService

    /**
     * @param impl Profile Service Implementation
     * @return Profile Service Interface
     */
    @Binds
    @Singleton
    abstract fun provideProfileService(
        impl: ProfileServiceImpl
    ): ProfileService

    /**
     * @param impl Review Service Implementation
     * @return Review Service Interface
     */
    @Binds
    @Singleton
    abstract fun provideReviewService(
        impl: ReviewServiceImpl
    ): ReviewService

    /**
     * @param impl Storage Service Implementation
     * @return Storage Service Interface
     */
    @Binds
    @Singleton
    abstract fun provideStorageService(
        impl: StorageServiceImpl
    ): StorageService

    /**
     * @param impl Account Service Implementation
     * @return Account Service Interface
     */
    @Binds
    @Singleton
    abstract fun provideAccountService(
        impl: AccountServiceImpl
    ): AccountService

    /**
     * @param impl Category Management Service Implementation
     * @return Category Management Service Interface
     */
    @Binds
    @Singleton
    abstract fun provideCategoryManagementService(
        impl: CategoryManagementServiceImpl
    ): CategoryManagementService

    /**
     * @param impl Offer Management Service Implementation
     * @return Offer Management Service Interface
     */
    @Binds
    @Singleton
    abstract fun provideOfferManagementService(
        impl: OfferManagementServiceImpl
    ): OfferManagementService

    /**
     * @param impl Conversation Management Service Implementation
     * @return Conversation Management Service Interface
     */
    @Binds
    @Singleton
    abstract fun provideConversationManagementService(
        impl: ConversationsManagementServiceImpl
    ): ConversationsManagementService

}