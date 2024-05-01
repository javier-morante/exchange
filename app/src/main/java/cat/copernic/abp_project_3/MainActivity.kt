package cat.copernic.abp_project_3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import cat.copernic.abp_project_3.application.presentation.MainScreen
import cat.copernic.abp_project_3.application.presentation.ui.theme.Abp_project_3Theme
import dagger.hilt.android.AndroidEntryPoint

/**
 * The entry point of the application.
 * It sets up the user interface using Jetpack Compose.
 */
@AndroidEntryPoint
class MainActivity: ComponentActivity() {
    /**
     * Called when the activity is starting.
     * Inflates the layout, applies the theme, and displays the main screen of the application.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     * this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     * Otherwise, it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Abp_project_3Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}