package cat.copernic.abp_project_3.application.data.module

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.Module
import dagger.Provides
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
 * The @Provides annotation indicates that the following method provides a dependency, so Dagger
 * will utilize this method for obtaining a instance from the provided dependency
 *
 * The @Singleton annotation ensures that the provided instance by the method is unique and
 * reused each time that the dependency is needed
 *
 */
@Module
@InstallIn(SingletonComponent::class)
class AuthenticationModule {

    /**
     * @return Instance of firebase authentication
     */
    @Provides
    @Singleton
    fun provideFirebaseAuthentication(): FirebaseAuth {
        return Firebase.auth
    }

}