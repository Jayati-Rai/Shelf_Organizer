package com.example.shelforganizer.navigation

sealed class NavGraph (val route: String ){
    object Login : NavGraph("Login")
    object SignUp : NavGraph("Signup")
    object Dashboard : NavGraph("Dashboard")
    object ItemScreen : NavGraph("itemScreen")

}