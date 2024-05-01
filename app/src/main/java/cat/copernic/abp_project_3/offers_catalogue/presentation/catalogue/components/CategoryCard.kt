package cat.copernic.abp_project_3.offers_catalogue.presentation.catalogue.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cat.copernic.abp_project_3.R
import coil.compose.AsyncImage

/**
 * Displays a clickable category card with an image and text.
 *
 * @param text The text to display on the category card.
 * @param image The image URL or resource ID for the category card.
 * @param onClick Callback triggered when the category card is clicked.
 * @param onLongClick Callback triggered when the category card is long-pressed.
 * @param modifier Modifier for the category card layout.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryCard(
    text:String,
    image:String,
    modifier: Modifier = Modifier,
    onClick:()->Unit = {},
    onLongClick:()->Unit = {},
) {
    Card(
        modifier = modifier
            .width(150.dp)
            .height(75.dp)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = image,
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, colorResource(R.color.transluciend_black)),
                            tileMode = TileMode.Clamp,
                            startY = 80.0f
                        )
                    ),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = text,
                    modifier = Modifier.padding(10.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CategoryCardPreview() {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        CategoryCard("","")
    }
}