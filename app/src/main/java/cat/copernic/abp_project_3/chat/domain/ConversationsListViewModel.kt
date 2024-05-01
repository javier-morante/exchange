package cat.copernic.abp_project_3.chat.domain

import androidx.compose.runtime.mutableStateListOf
import cat.copernic.abp_project_3.application.domain.ApplicationViewModel
import cat.copernic.abp_project_3.application.data.model.Conversation
import cat.copernic.abp_project_3.application.data.model.Profile
import cat.copernic.abp_project_3.application.data.service.authentication.AuthenticationService
import cat.copernic.abp_project_3.application.data.service.conversation.ConversationService
import cat.copernic.abp_project_3.application.data.service.profile.ProfileService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * ViewModel responsible for managing the state and business logic related to the list of conversations.
 *
 * This ViewModel integrates with authentication, profiles, and conversation services to gather and manage
 * the list of conversations associated with the authenticated user.
 *
 * @param authenticationService The service responsible for handling authentication.
 * @param profileService The service used to retrieve and manage user profiles.
 * @param conversationService The service for interacting with conversation-related data and operations.
 */
@HiltViewModel
class ConversationsListViewModel @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val profileService: ProfileService,
    private val conversationService: ConversationService
): ApplicationViewModel() {

    private var _currentAuthUserUid = MutableStateFlow(authenticationService.currentAuthUserUid)
    val currentAuthUserUid get() = _currentAuthUserUid.asStateFlow()


    private val _conversationsList = mutableStateListOf<Conversation>()
    val conversationsList get() = _conversationsList.toList()

    /**
     * Initializes the ViewModel by collecting the list of conversations associated with the authenticated user.
     */
    init {
        collectConversationsList()
    }

    /**
     * Collects and populates the list of conversations associated with the authenticated user.
     */
    private fun collectConversationsList() {
        launchCatching {
            conversationService.actualAuthUserConversationsList.collect { conversations ->
                for(conversation in conversations) {
                    for(profileRef in conversation.usersRef) {
                        if(profileRef != _currentAuthUserUid.value) {
                            val profile = profileService.getProfile(profileRef) ?: Profile()
                            conversation.userProfile = profile
                        }
                    }
                }

                _conversationsList.clear()
                _conversationsList.addAll(conversations)
            }
        }
    }

}