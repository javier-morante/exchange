package cat.copernic.abp_project_3.offers_catalogue.presentation.catalogue.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Displays a list of radio buttons based on provided options.
 *
 * @param radioOptions List of options to display as radio buttons.
 * @param selectedOption Index of the currently selected option (null if none selected).
 * @param onOptionSelected Callback triggered when an option is selected.
 */
@Composable
fun RadioButtons(
    radioOptions:List<String>,
    selectedOption:Int?,
    onOptionSelected:(Int?)->Unit
) {
    Column (
        Modifier.verticalScroll(rememberScrollState())
    ){
        radioOptions.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (radioOptions.indexOf(text) == selectedOption),
                        onClick = {
                            onOptionSelected(radioOptions.indexOf(text))
                        }
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (radioOptions.indexOf(text) == selectedOption),
                    onClick = { onOptionSelected(radioOptions.indexOf(text)) }
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp),
                    fontSize = 20.sp
                )
            }
        }
    }
}
