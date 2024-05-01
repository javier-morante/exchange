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
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cat.copernic.abp_project_3.R
import cat.copernic.abp_project_3.application.presentation.components.permision_handlers.RequestLocationPermission

/**
 * Displays a dialog with a range slider for selecting a numerical range.
 *
 * @param title The title displayed on the button.
 * @param minimumRange The minimum value of the selectable range.
 * @param maximumRange The maximum value of the selectable range.
 * @param selectedRange The currently selected range (null if none selected).
 * @param onChangeRange Callback triggered when the range is changed.
 * @param onChangeLocationPermission Callback triggered when location permission is changed.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ButtonSliderDialog(
    title: String,
    minimumRange: Float,
    maximumRange: Float,
    selectedRange: ClosedFloatingPointRange<Float>?,
    onChangeRange: (ClosedFloatingPointRange<Float>?) -> Unit,
    onChangeLocationPermission: (Boolean) -> Unit = {}
) {
    var range by remember {
        mutableStateOf(minimumRange..maximumRange)
    }
    var isOpenDialog by rememberSaveable {
        mutableStateOf(false)
    }

    Row {
        OutlinedButton(
            onClick = { isOpenDialog = true },
            modifier = Modifier.widthIn(min = 150.dp),
            border = BorderStroke(
                2.dp,
                MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                text = if (selectedRange != null) stringResource(
                    id = R.string.distance_between,
                    selectedRange.start,
                    selectedRange.endInclusive
                ) else title
            )
        }
    }
    BottomDialog(isOpenDialog = isOpenDialog, closeDialog = { isOpenDialog = false }) {
        RequestLocationPermission(onPermissionDenied = {
            it()
            onChangeLocationPermission(false)
        }, onPermissionReady = { onChangeLocationPermission(true) })

        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CenterAlignedTopAppBar(title = { Text(text = "") }, actions = {
                IconButton(onClick = { it() }) {
                    Icon(
                        Icons.Rounded.Clear,
                        contentDescription = null
                    )
                }
            })
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = if (selectedRange != null) Arrangement.SpaceBetween else Arrangement.End
            ) {
                if (selectedRange != null) {
                    Button(onClick = {
                        onChangeRange(null)
                        range = minimumRange..maximumRange
                    }) {
                        Text(text = stringResource(id = R.string.btn_clear))
                    }
                }
                Button(onClick = {
                    onChangeRange(range)
                    it()
                }) {
                    Text(text = stringResource(id = R.string.btn_apply))
                }
            }
            RangeSlider(
                value = range,
                onValueChange = { range = it },
                valueRange = minimumRange..maximumRange,
                onValueChangeFinished = {},
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = stringResource(
                    id = R.string.distance_between,
                    range.start,
                    range.endInclusive
                ),
                fontSize = 30.sp
            )
            Spacer(modifier = Modifier.height(60.dp))
        }

    }
}