package cat.copernic.abp_project_3.application.presentation.components.divider

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 *  Composable method that shows a divider is use to put between elements
 *
 * @param modifier
 * @param space
 */
@Composable
fun CustomDivider(
    modifier: Modifier = Modifier,
    space: Dp = 0.dp
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(space))
        Divider(
            modifier = modifier,
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(space))
    }
}