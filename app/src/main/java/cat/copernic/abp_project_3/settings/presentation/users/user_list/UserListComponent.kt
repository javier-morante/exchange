package cat.copernic.abp_project_3.settings.presentation.users.user_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cat.copernic.abp_project_3.R
import cat.copernic.abp_project_3.application.presentation.components.list_item.ManagersListItemComponent
import cat.copernic.abp_project_3.application.presentation.components.search_bar.SearchBarComp
import cat.copernic.abp_project_3.application.data.model.Profile

/**
 * Component that contains all the referent ui elements for the users list
 *
 * @param handleMenuBackNavigation
 * @param usersListComponent
 * @param handleDetails
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListComponent(
    handleMenuBackNavigation: () -> Unit,
    usersListComponent: List<Profile>,
    handleDetails: (String) -> Unit
) {
    var filterQuery by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                ),
                navigationIcon = {
                    IconButton(
                        onClick = handleMenuBackNavigation
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = stringResource(R.string.back_button_helper)
                        )
                    }
                },
                title = {
                    Text(
                        style = MaterialTheme.typography.titleLarge,
                        text = stringResource(R.string.user_management_screen_title)
                    )
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
            ) {
                // Search Bar Component
                SearchBarComp(
                    modifier = Modifier.fillMaxWidth(),
                    handleSearch = { filterQuery = it }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Users List
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                ) {
                    items(
                        usersListComponent.filter {
                            try {
                                val empty = filterQuery.isEmpty()
                                val pattern = "^$filterQuery".toRegex()
                                "${it.name} ${it.surname}".matches(pattern) || empty
                            } catch (e: Exception) {
                                false
                            }
                        }
                    ) { item ->
                        ManagersListItemComponent(
                            title = "${item.name} ${item.surname}",
                            imageUrl = item.imageUrl,
                            onClick = { handleDetails(item.id) }
                        )
                    }
                }
            }
        }

    }
}