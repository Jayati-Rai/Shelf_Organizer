package com.example.shelforganizer.screens

import android.widget.Toast
import androidx.collection.emptyLongSet
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.shelforganizer.R
import com.example.shelforganizer.authentication.loginUser
import com.example.shelforganizer.authentication.registerUser
import com.example.shelforganizer.navigation.NavGraph
import kotlinx.coroutines.launch

//@Preview(showBackground = true)
@Composable
fun LoginScreen(navController: NavHostController) {
    val context= LocalContext.current
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisibilityValue by rememberSaveable{
        mutableStateOf(false)
    }

//    val context= LocalContext.current
Surface (color = Color.White){


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp, top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(id = R.drawable.mobile_login_cristina),
            contentDescription = "Login Image",
            modifier=Modifier.size(200.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { newText -> email = newText },
            label = { Text("Email") },

        )
        OutlinedTextField(
            value = password,
            onValueChange = { newText -> password = newText },
            label = { Text("Password")},
            trailingIcon = {IconButton(onClick = { passwordVisibilityValue=!passwordVisibilityValue }) {
                if(passwordVisibilityValue){
                     Icon(painter = painterResource(id = R.drawable.visibility_icon_off), contentDescription ="Visibility Icon" )}
                else{
                    Icon(painter = painterResource(id = R.drawable.visibility_icon_512x349_83wdi4gx), contentDescription ="Visibility Icon" )
                }
            }
            },
            visualTransformation = if(passwordVisibilityValue) androidx.compose.ui.text.input.VisualTransformation.None else androidx.compose.ui.text.input.PasswordVisualTransformation()
        )

        Button(
            onClick = {
                if (email.isBlank() || password.isBlank()) {
                   Toast.makeText(context,"Email and Password cannot be empty!",Toast.LENGTH_SHORT).show()
                }
               else{ loginUser(email,password,onSuccess = {
                    println("Login Successful!")
                    navController.navigate(NavGraph.Dashboard.route)
                }, onFailure =
                {error->
                    println("Login Failed!$error")
                }
                )
            }},
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
        ) {
            Text(text = "Login")
        }

        Text(text = "Don't have an account? Create one.",
            modifier = Modifier.clickable { navController.navigate(NavGraph.SignUp.route) })
        Text(text = "Forgot Password?")
    }
}
}
@Composable
fun SignUpScreen(navController: NavHostController) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
//    val context= LocalContext.current

    Surface(color = Color.White) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp, top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.login),
                contentDescription = "Login Image",
                modifier = Modifier.size(200.dp)
            )


            OutlinedTextField(
                value = username,
                onValueChange = { newText -> username = newText },
                label = { Text("Username") }
            )
            OutlinedTextField(
                value = email,
                onValueChange = { newText -> email = newText },
                label = { Text("Email") }
            )
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { newText -> phoneNumber = newText },
                label = { Text("Phone number") }
            )
            OutlinedTextField(
                value = newPassword,
                onValueChange = { newText -> newPassword = newText },
                label = { Text("New Password") }
            )
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { newText -> confirmPassword = newText },
                label = { Text("Confirm Password") }
            )

            Button(
                onClick = {
                    coroutineScope.launch {
                        registerUser(username, email, newPassword, navController)
                    }

                },
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
            ) {
                Text(text = "Sign Up")
            }

            Text(
                text = "Existing User? Login.",
                modifier = Modifier.clickable {
                    navController.navigate(NavGraph.Login.route)
                }
            )
        }
    }
}