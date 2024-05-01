package cat.copernic.abp_project_3.offers_catalogue.presentation.catalogue.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cat.copernic.abp_project_3.R
import cat.copernic.abp_project_3.application.presentation.components.search_bar.SearchBarComp
import cat.copernic.abp_project_3.application.data.model.Category
import coil.compose.AsyncImage

/**
 * Displays the top bar of a catalogue screen with search bar and category cards.
 *
 * @param selectedOption The currently selected category option (null if none selected).
 * @param options List of available categories to display as category cards.
 * @param modifier Modifier for the top-level layout.
 * @param categoryDialog Default category for the dialog (optional).
 * @param onChangeCategoryDialog Callback triggered when a category is selected for dialog.
 * @param onChangeCategory Callback triggered when a category is selected.
 * @param onTextFieldTextChanged Callback triggered when the text in the search bar changes.
 */
@Composable
fun CatalogueTopBar(
    selectedOption: Category?,
    options: List<Category>,
    modifier: Modifier = Modifier,
    categoryDialog: Category = Category(),
    onChangeCategoryDialog: (Category) -> Unit = {},
    onChangeCategory: (Category?) -> Unit,
    onTextFieldTextChanged: (String) -> Unit,

    ) {
    var isOpenDialog by rememberSaveable {
        mutableStateOf(false)
    }

    Box(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SearchBarComp(
                modifier = Modifier.fillMaxWidth()
            ) {
                onTextFieldTextChanged(it)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (selectedOption != null) {
                    IconButton(
                        onClick = {
                            onChangeCategory(null)
                            isOpenDialog = false
                        },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.un_filter_icon),
                            contentDescription = "",
                            tint = colorResource(id = R.color.primaryColor)
                        )
                    }
                }
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    items(options) {
                        CategoryCard(
                            text = it.title,
                            image = it.imageUrl,
                            onClick = {
                                onChangeCategory(it)
                            },
                            onLongClick = {
                                onChangeCategoryDialog(it)
                                isOpenDialog = true
                            },
                            modifier = Modifier.padding(5.dp)
                        )
                    }
                }
                
            }

            BottomDialog(
                isOpenDialog = isOpenDialog,
                closeDialog = { isOpenDialog = false }) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {}
                ) {
                    Column(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                                .padding(16.dp)
                        ) {
                            AsyncImage(
                                model = categoryDialog.imageUrl,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(MaterialTheme.shapes.medium),
                                contentScale = ContentScale.Crop
                            )

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(Color.Transparent, Color.Black),
                                            startY = 100f
                                        )
                                    ),
                                contentAlignment = Alignment.BottomStart
                            ) {
                                Text(
                                    text = categoryDialog.title,
                                    fontSize = 40.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(10.dp),
                                    color = Color.White
                                )
                            }
                        }
                        Text(
                            text = categoryDialog.description,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .padding(horizontal = 16.dp))
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}