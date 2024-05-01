package cat.copernic.abp_project_3.offers_catalogue.presentation.catalogue.components

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

/**
 * Displays a modal bottom sheet dialog.
 *
 * @param isOpenDialog Whether the dialog should be open or closed.
 * @param closeDialog Callback to close the dialog.
 * @param content The composable content to be displayed inside the dialog.
 */
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun BottomDialog(
    isOpenDialog:Boolean,
    closeDialog:()->Unit,
    content: @Composable (()->Unit)->Unit
) {
    val sheepStatus = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val close:()->Unit = {
        scope.launch(
            CoroutineExceptionHandler{_,trowable ->
                Log.e("Dialog Error","Error on close")
            }
        ){
            sheepStatus.hide()
            closeDialog()
        }
    }

    val open = {
        scope.launch(
            CoroutineExceptionHandler{_,trowable ->
                Log.e("Dialog Error","Error on open")
            }
        ){
            sheepStatus.expand()
        }
    }



    if (isOpenDialog) {
        open()
        ModalBottomSheet(onDismissRequest = close, sheetState = sheepStatus){
            content(close)
        }
    }
}