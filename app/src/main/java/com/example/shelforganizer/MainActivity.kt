package com.example.shelforganizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shelforganizer.navigation.NavGraph
import com.example.shelforganizer.screens.DashboardScreen
import com.example.shelforganizer.screens.ItemDisplayScreen
import com.example.shelforganizer.screens.LoginScreen
import com.example.shelforganizer.screens.SignUpScreen
import com.example.shelforganizer.ui.theme.ShelfOrganizerTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShelfOrganizerTheme {
                val auth = FirebaseAuth.getInstance()
                val currentUser = auth.currentUser
                val startDestination = if (currentUser != null) NavGraph.Dashboard.route else NavGraph.Login.route
                    val navController = rememberNavController()
                NavHost(navController = navController, startDestination = startDestination ){
                    composable(NavGraph.Login.route) {
                        LoginScreen(navController)
                    }
                    composable(route= NavGraph.SignUp.route)
                    {
                        SignUpScreen(navController)
                    }
                    composable(route= NavGraph.Dashboard.route)
                    {
                        DashboardScreen(navController)
                    }
                    composable(route= NavGraph.ItemScreen.route)
                    {
                        ItemDisplayScreen()
                    }
                }

            }
        }
    }
}




