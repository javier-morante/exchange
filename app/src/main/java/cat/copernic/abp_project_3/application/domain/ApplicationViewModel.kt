package cat.copernic.abp_project_3.application.domain

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Base class for ViewModels in the application.
 */
open class ApplicationViewModel() : ViewModel() {

    /**
     * Launches a coroutine, catching any error that occurs,
     * and logs the error in the logcat.
     *
     * @param block The code to execute as a coroutine.
     */
    fun launchCatching(
        block: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                Log.d("APP ERROR", "", throwable)
            },
            block = block
        )
    }

    /**
     * Launches a coroutine, catching any error that occurs,
     * logs the error in the logcat, and displays a provided toast message.
     *
     * @param displayToast A function to display a toast message.
     * @param block The code to execute as a coroutine.
     */
    fun launchCatching(
        displayToast: (String) -> Unit,
        block: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                Log.d("APP ERROR", throwable.message.orEmpty())
                displayToast(throwable.message.orEmpty())
            },
            block = block
        )
    }

}