package cat.copernic.abp_project_3.settings.presentation.categories.create_category

import android.net.Uri
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cat.copernic.abp_project_3.R
import cat.copernic.abp_project_3.application.presentation.components.button.ConfirmButtonComp
import cat.copernic.abp_project_3.application.presentation.components.divider.CustomDivider
import cat.copernic.abp_project_3.application.presentation.components.resource_picker.ImagePicker
import cat.copernic.abp_project_3.application.presentation.components.text_field.CustomLongTextField
import cat.copernic.abp_project_3.application.presentation.components.text_field.CustomTextField
import cat.copernic.abp_project_3.settings.domain.categories.CategoryCreationViewModel

/**
 * Hoisted class that contains the viewModel injection and a reference to the
 * CategoryCreationComponentUI composable
 *
 * @param categoryCreationViewModel
 * @param displayNotification
 * @param handleBackNavigation
 */
@Composable
fun CategoryCreationComponent(
    categoryCreationViewModel: CategoryCreationViewModel = hiltViewModel(),
    displayNotification: (String) -> Unit,
    handleBackNavigation: () -> Unit,
) {

    val categoryCreationState by categoryCreationViewModel.categoryCreationState.collectAsState()


    CategoryCreationComponentUI(
        handleBackNavigation = handleBackNavigation,
        categoryCreationState,
        onTitleChange = { categoryCreationViewModel.setTitle(it) },
        onDescriptionChange = { categoryCreationViewModel.setDescription(it) },
        onImageChange = { categoryCreationViewModel.setCategoryImage(it) },
        handleSubmit = {
            categoryCreationViewModel.handleCategoryCreation(
                displayToast = displayNotification,
                handleBackNavigation = handleBackNavigation
            )

        }
    )
}

/**
 * Component that contains all the ui elements referent to the Category Creation Screen
 *
 * @param handleBackNavigation
 * @param categoryCreationState
 * @param onTitleChange
 * @param onDescriptionChange
 * @param onImageChange
 * @param handleSubmit
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryCreationComponentUI(
    handleBackNavigation: () -> Unit,
    categoryCreationState: CategoryCreationState,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onImageChange: (Uri?) -> Unit,
    handleSubmit: () -> Unit
) {
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
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                // Image Picker Field
                ImagePicker(
                    title = stringResource(R.string.category_creation_image_picker_label),
                    profileImageUrl = categoryCreationState.categoryData.imageUrl,
                    handleImageChange = onImageChange
                )

                CustomDivider()

                // Title Text Field
                CustomTextField(
                    title = stringResource(R.string.category_creation_title_label),
                    value = categoryCreationState.categoryData.title,
                    onValueChange = onTitleChange
                )

                CustomDivider()

                // Description Text Field
                CustomLongTextField(
                    title = stringResource(R.string.category_creation_description_label),
                    value = categoryCreationState.categoryData.description,
                    onValueChange = onDescriptionChange
                )
            }

            // Create category button
            ConfirmButtonComp(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.BottomCenter),
                title = stringResource(R.string.category_creation_create_button_label),
                onClick = handleSubmit
            )
        }
    }
}