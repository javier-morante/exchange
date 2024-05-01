package cat.copernic.abp_project_3.chat.presentation.conversations_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cat.copernic.abp_project_3.R
import cat.copernic.abp_project_3.chat.domain.ConversationsListViewModel
import cat.copernic.abp_project_3.chat.presentation.ConversationsBaseNav
import cat.copernic.abp_project_3.chat.presentation.components.ConversationListComponent
import cat.copernic.abp_project_3.application.presentation.components.label.CustomTitle
import cat.copernic.abp_project_3.application.data.model.Conversation

/**
 * Composable function for displaying a the data of viewModel about conversations
 *
 * @param conversationsListViewModel The view model responsible for managing conversations list data.
 * @param handleNavigation A lambda function to handle navigation to a specific conversation.
 */
@Composable
fun ConversationsListScreen(
    conversationsListViewModel: ConversationsListViewModel = hiltViewModel(),
    handleNavigation: (String) -> Unit
) {
    val conversationsList = conversationsListViewModel.conversationsList

    val currentAuthUserUid by conversationsListViewModel.currentAuthUserUid.collectAsState()

    ConversationsListScreenUI(
        currentAuthUserUid = currentAuthUserUid ?: "",
        conversationsList = conversationsList,
        handleNavigation = handleNavigation
    )
}

/**
 * Composable function for displaying the UI of a conversations list.
 *
 * @param currentAuthUserUid The unique identifier of the current authenticated user.
 * @param conversationsList The list of conversations to display.
 * @param handleNavigation A lambda function to handle navigation to a specific conversation.
 */
@Composable
fun ConversationsListScreenUI(
    currentAuthUserUid: String,
    conversationsList: List<Conversation>,
    handleNavigation: (String) -> Unit
) {
    Column {
        CustomTitle(
            modifier = Modifier.padding(16.dp),
            title = stringResource(R.string.conversations_list_title)
        )

        LazyColumn {
            items(conversationsList) { item ->
                ConversationListComponent(
                    title = "${item.userProfile.name} ${item.userProfile.surname}",
                    imageUrl = item.userProfile.imageUrl,
                    onClick = {
                        for (ref in item.usersRef) {
                            if(currentAuthUserUid != ref) {
                                handleNavigation("${ConversationsBaseNav.CONVERSATION.name}/${item.id}/${ref}")
                            }
                        }
                    }
                )
            }
        }
    }
}