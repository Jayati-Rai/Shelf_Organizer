    package com.example.shelforganizer.screens

    import android.util.Log
    import androidx.compose.foundation.clickable
    import androidx.compose.foundation.layout.Arrangement
    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.fillMaxWidth
    import androidx.compose.foundation.layout.padding
    import androidx.compose.foundation.lazy.LazyColumn
    import androidx.compose.material3.AlertDialog
    import androidx.compose.material3.Button
    import androidx.compose.material3.Card
    import androidx.compose.material3.CircularProgressIndicator
    import androidx.compose.material3.OutlinedTextField
    import androidx.compose.material3.Text
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.LaunchedEffect
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.saveable.rememberSaveable
    import androidx.compose.runtime.setValue
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.platform.LocalContext
    import androidx.compose.ui.unit.dp
    import androidx.navigation.NavController
    import com.example.shelforganizer.component.SearchableTopAppBar
    import com.example.shelforganizer.component.addPlace
    import com.example.shelforganizer.databaseConnectivity.fetchPlaceNames
    import com.example.shelforganizer.navigation.NavGraph
    import com.google.firebase.auth.FirebaseAuth

    //@Preview(showBackground = true)



    @Composable
    fun DashboardScreen(navController: NavController) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        var placeNames by rememberSaveable { mutableStateOf<List<String>>(emptyList()) }
        var placeIds by rememberSaveable { mutableStateOf<List<String>>(emptyList()) }
        var newPlace by rememberSaveable {
            mutableStateOf("")
        }
        //var isLoading by rememberSaveable { mutableStateOf(true) }
        var error by rememberSaveable { mutableStateOf<String?>(null) }
        //var check by rememberSaveable { mutableIntStateOf(0) }
        var showDialog by rememberSaveable {
            mutableStateOf(false)
        }
        Log.d("FirebaseAuth", "Current User ID: $userId")
        Column {
            SearchableTopAppBar(navController = navController)
            userId?.let {
                LaunchedEffect(it) {
                    fetchPlaceNames(it, onSuccess =
                    {names,id-> placeNames = names
                        placeIds=id},
                        onFailure = { exception -> error = exception.message })
                }
            }
            when {
                placeNames == null -> CircularProgressIndicator() // Show loading indicator
                error != null -> Text(text = "Error: $error", color = Color.Red)
                placeNames.isEmpty() -> NoPlacesFound { showDialog = true }
                else -> PlacesList(navController,placeNames,placeIds)
            }
            if (showDialog) {
                AddPlaceDialog(onDismiss = { showDialog = false },
                    onConfirm = { newPlace ->
                        if (userId != null) {
                            addPlace(newPlace)
                            showDialog = false // Close dialog after adding
                        }
                    }
                )
            }
        }
    }


    @Composable
    fun PlacesList(navController: NavController,placeNames: List<String>,placesId:List<String>) {
        LazyColumn {
            items(placeNames.size) { name ->
                PlaceItem(navController,name = placeNames[name],id=placesId[name])
            }
        }
    }

    @Composable
    fun PlaceItem(navController: NavController,name: String,id:String) {
        val context=LocalContext.current
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable {
                    navController.navigate(NavGraph.ItemScreen.route)
                }
        ) {
           Text(text = name)
        }
    }


    @Composable
    fun NoPlacesFound(onAddClick:()->Unit) {
    Column(Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally) {
    Text(text = "No Place Found!\nCreate one to get started!")
    Button(onClick = { onAddClick() }) {
        Text(text = "Add Place")
    }
        }
    }

    @Composable
    fun AddPlaceDialog(onDismiss: () -> Unit,onConfirm: (String)->Unit) {
        var newPlace by rememberSaveable {
            mutableStateOf("")
        }

        AlertDialog(onDismissRequest =onDismiss,
            title = { Text("Add New Place")},
             text = {
            OutlinedTextField(
            value = newPlace,
            onValueChange = { newPlace = it },
            label = { Text("Enter place name") }
            )
             },
            confirmButton = {
                    Button(onClick = { if(newPlace.isNotBlank())onConfirm(newPlace) })
                    {
                        Text("Confirm")
                    }
                }

        )
    }
    




