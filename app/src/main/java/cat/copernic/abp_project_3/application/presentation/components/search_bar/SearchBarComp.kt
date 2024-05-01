package cat.copernic.abp_project_3.application.presentation.components.search_bar

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import cat.copernic.abp_project_3.R
import cat.copernic.abp_project_3.application.presentation.components.button.ConfirmButtonComp

/**
 * Composable function representing a search bar component with an outlined text field and search functionality.
 * This component allows users to enter search queries and triggers search actions on input changes or button clicks.
 *
 * @param modifier The modifier to be applied to the search bar.
 * @param handleSearch The callback function invoked when a search query is submitted.
 */
@Composable
fun SearchBarComp(
    modifier: Modifier = Modifier,
    handleSearch: (query: String) -> Unit
) {

    var query by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        value = query,
        onValueChange = { query = it },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                handleSearch(query)
            }
        ),
        shape = MaterialTheme.shapes.medium,
        maxLines = 1,
        trailingIcon = {
            ConfirmButtonComp(
                modifier = Modifier.padding(end = 8.dp),
                icon = painterResource(R.drawable.baseline_search),
                iconDescription = stringResource(R.string.search_bar_button_helper),
                onClick = {
                    handleSearch(query)
                }
            )
        },
        colors = TextFieldDefaults.colors(
            disabledTextColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        modifier = modifier
            .border(
                2.dp,
                MaterialTheme.colorScheme.primaryContainer,
                MaterialTheme.shapes.medium
            ),
        placeholder = {
            Text(
                text = stringResource(R.string.category_search_box_placeholder_label)
            )
        }
    )
}