package cat.copernic.abp_project_3.chat.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cat.copernic.abp_project_3.chat.presentation.conversation_messages.ConversationMessagesScreen
import cat.copernic.abp_project_3.chat.presentation.conversations_list.ConversationsListScreen

/**
 * Enum class representing base navigation destinations for conversations.
 */
enum class ConversationsBaseNav {
    /**
     * Represents navigation to the conversations list.
     */
    LIST,
    /**
     * Represents navigation to an individual conversation
     */
    CONVERSATION
}

/**
 * This Composable function represents the main screen for managing  conversations navigation.
 *
 * @param navController The navigation controller used for navigating between composables.
 */
@Composable
fun ConversationsScreen(
    navController: NavHostController = rememberNavController(),
    displayNotification: (String) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = ConversationsBaseNav.LIST.name
    ) {
        composable(ConversationsBaseNav.LIST.name) {
            ConversationsListScreen(
                handleNavigation = {
                    navController.navigate(it)
                }
            )
        }
        composable(
            route = "${ConversationsBaseNav.CONVERSATION.name}/{conversationId}/{profileId}",
            arguments = listOf(
                navArgument("conversationId") { type = NavType.StringType },
                navArgument("profileId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            ConversationMessagesScreen(
                conversationId = backStackEntry.arguments?.getString("conversationId") ?: "",
                profileId = backStackEntry.arguments?.getString("profileId") ?: "",
                displayNotification = displayNotification,
                handleBackNavigation = {
                    navController.popBackStack()
                }
            )
        }
    }
}