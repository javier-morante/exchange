package cat.copernic.abp_project_3.application.presentation.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import cat.copernic.abp_project_3.R

/**
 *  Composable method, display a outlined red button
 *
 * @param modifier
 * @param title
 * @param onClick
 */
@Composable
fun DangerButtonComp(
    modifier: Modifier,
    title: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        shape = MaterialTheme.shapes.small,
        onClick = onClick,
        modifier = modifier,
        border = BorderStroke(2.dp, colorResource(id = R.color.danger)),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = colorResource(id = R.color.danger)
        )
    ) {
        Text(
            style = MaterialTheme.typography.titleMedium,
            text = title
        )
    }
}