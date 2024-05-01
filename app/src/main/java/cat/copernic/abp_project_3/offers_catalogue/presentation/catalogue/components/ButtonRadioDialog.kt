package cat.copernic.abp_project_3.offers_catalogue.presentation.catalogue.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cat.copernic.abp_project_3.R

/**
 * Displays a dialog with radio button options and apply/clear buttons.
 *
 * @param title The title displayed on the button.
 * @param titleDialog The title displayed inside the dialog.
 * @param radioOptions List of options to display as radio buttons.
 * @param selectedOption Currently selected option index (null if none selected).
 * @param onOptionSelect Callback triggered when an option is selected.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ButtonRadioDialog(
    title: String = "",
    titleDialog: String = "",
    radioOptions: List<String>,
    selectedOption: Int?,
    onOptionSelect: (Int?) -> Unit
) {
    var selection by rememberSaveable {
        mutableStateOf(selectedOption)
    }
    var isOpenDialog by rememberSaveable {
        mutableStateOf(false)
    }

    Row {
        OutlinedButton(
            onClick = { isOpenDialog = true }, Modifier.widthIn(min = 150.dp),
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
        ) {
            Text(text = if (selectedOption != null) radioOptions[selectedOption] else title)
        }
    }
    BottomDialog(isOpenDialog = isOpenDialog, closeDialog = { isOpenDialog = false }) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            CenterAlignedTopAppBar(title = { Text(text = titleDialog) }, actions = {
                IconButton(onClick = { it() }) {
                    Icon(
                        Icons.Rounded.Clear,
                        contentDescription = null
                    )
                }
            })
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = if (selectedOption != null) Arrangement.SpaceBetween else Arrangement.End
            ) {
                if (selectedOption != null) {
                    Button(onClick = {
                        onOptionSelect(null)
                        selection = null
                    }) {
                        Text(text = stringResource(id = R.string.btn_clear))
                    }
                }
                Button(onClick = {
                    onOptionSelect(selection)
                    it()
                }) {
                    Text(text = stringResource(id = R.string.btn_apply))
                }
            }
            RadioButtons(
                radioOptions = radioOptions,
                selectedOption = selection,
                onOptionSelected = { selection = it }
            )
            Spacer(modifier = Modifier.height(60.dp))
        }

    }
}

