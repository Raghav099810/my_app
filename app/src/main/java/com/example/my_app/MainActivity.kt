package com.example.my_app
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.my_app.splashscreen.SplashScreen
import com.example.my_app.ui.theme.MyAppTheme
import com.example.my_app.navigation.Screen
import com.example.my_app.bottomnavigation.BottomNavigationScreen  // Assuming BottomNavigation is defined elsewhere

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.SplashScreen.route
                    ) {
                        composable(Screen.SplashScreen.route) {
                            SplashScreen(navController)
                        }
                        composable(Screen.BottomNavigation.route) {
                            BottomNavigationScreen() // The BottomNavigation composable
                        }
                    }
                }
            }
        }
    }
}
