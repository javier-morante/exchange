package cat.copernic.abp_project_3.settings.presentation.categories.category_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cat.copernic.abp_project_3.R
import cat.copernic.abp_project_3.application.presentation.components.button.ConfirmButtonComp
import cat.copernic.abp_project_3.application.presentation.components.button.DangerButtonComp
import cat.copernic.abp_project_3.application.presentation.components.divider.CustomDivider
import cat.copernic.abp_project_3.application.presentation.components.resource_picker.ImagePicker
import cat.copernic.abp_project_3.application.presentation.components.text_field.CustomLongTextField
import cat.copernic.abp_project_3.application.presentation.components.text_field.CustomTextField
import cat.copernic.abp_project_3.settings.domain.categories.CategoryDetailsViewModel

/**
 * Component that contains a viewModel injection and all the ui elements referent to the
 * Category Details Screen
 *
 * @param categoryDetailsViewModel
 * @param displayNotification
 * @param handleBackNavigation
 * @param categoryId
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDetailsComponent(
    categoryDetailsViewModel: CategoryDetailsViewModel = hiltViewModel(),
    displayNotification: (String) -> Unit,
    handleBackNavigation: () -> Unit,
    categoryId: String?,
) {

    val categoryDetailsState by categoryDetailsViewModel.categoryDetailsState.collectAsState()

    LaunchedEffect(key1 = categoryId) {
        if(categoryId != null) {
            categoryDetailsViewModel.getCategoryData(categoryId)
        }else {
            handleBackNavigation()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                ),
                navigationIcon = {
                    IconButton(
                        onClick = handleBackNavigation
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = stringResource(R.string.back_button_helper)
                        )
                    }
                },
                title = {
                    Text(
                        style = MaterialTheme.typography.titleLarge,
                        text = stringResource(R.string.category_management_title)
                    )
                }
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
            ) {
                // Image Picker Field
                ImagePicker(
                    title = stringResource(R.string.category_creation_image_picker_label),
                    profileImageUrl = categoryDetailsState.categoryData.imageUrl,
                    handleImageChange = { categoryDetailsViewModel.setCategoryImage(it) }
                )

                CustomDivider()
                
                // Title text Field
                CustomTextField(
                    title = stringResource(R.string.category_creation_title_label),
                    value = categoryDetailsState.categoryData.title,
                    onValueChange = { categoryDetailsViewModel.setTitle(it) }
                )

                CustomDivider()

                // Description Text Field
                CustomLongTextField(
                    title = stringResource(R.string.category_creation_description_label),
                    value = categoryDetailsState.categoryData.description,
                    onValueChange = { categoryDetailsViewModel.setDescription(it) }
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .clip(MaterialTheme.shapes.medium)
                    .background(Color.White)
            ) {
                // Submit Button
                ConfirmButtonComp(
                    modifier = Modifier
                        .fillMaxWidth()
                    ,
                    title = stringResource(R.string.category_details_update_button_label),
                    onClick = {
                        categoryDetailsViewModel.handleUpdateCategoryData(displayNotification)
                    }
                )
                // Delete Button
                DangerButtonComp(
                    modifier = Modifier
                        .fillMaxWidth(),
                    title = stringResource(R.string.category_details_delete_button_label),
                    onClick = {
                        categoryDetailsViewModel.handleDeleteCategory(
                            displayNotification,
                            handleBackNavigation
                        )
                    }
                )
            }

        }
    }
}