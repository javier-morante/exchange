package cat.copernic.abp_project_3.offers_catalogue.presentation.catalogue.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cat.copernic.abp_project_3.R

/**
 * Displays an expandable filters section with a toggle button and content.
 *
 * @param content The content to display within the filters section.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandFilters(
    content: @Composable () -> Unit
) {
    var isOpen by rememberSaveable {
        mutableStateOf(false)
    }
    Scaffold(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .height(if (isOpen) 120.dp else 50.dp),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "") },
                actions = {
                    IconButton(onClick = { isOpen = !isOpen }) {
                        if (isOpen) Icon(
                            painterResource(id = R.drawable.filter_list_off),
                            contentDescription = stringResource(R.string.close_filters)
                        ) else Icon(
                            painterResource(id = R.drawable.filter_list_on),
                            contentDescription = stringResource(R.string.open_filters)
                        )
                    }
                },
                modifier = Modifier
                    .height(50.dp)
                    .clickable(
                        interactionSource = remember {
                            MutableInteractionSource()
                        },
                        indication = null
                    ) {
                        isOpen = !isOpen
                    },
            )
        },
    ) {
        Row(
            modifier = Modifier
                .padding(it)
                .horizontalScroll(rememberScrollState())
        ) {
            content()
        }
    }
}