package cat.copernic.abp_project_3.application.presentation.components.label

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import cat.copernic.abp_project_3.R

@Composable
fun AuthTitleComponent(
    modifier: Modifier,
    title: String,
    subtitle: String,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold,
            text = title
        )
        Text(
            style = MaterialTheme.typography.titleLarge,
            color = colorResource(R.color.subtitle),
            text = subtitle
        )
    }
}