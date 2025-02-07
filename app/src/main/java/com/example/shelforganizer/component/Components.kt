package com.example.shelforganizer.component

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.navigation.NavController
import com.example.shelforganizer.navigation.NavGraph
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchableTopAppBar(navController: NavController,
    title: String = "My App",
    onSearch: (String) -> Unit = {}
) {
    var searchText by rememberSaveable { mutableStateOf("") }
    var isSearching by rememberSaveable { mutableStateOf(false) }
    var isAddMenuOpen by rememberSaveable { mutableStateOf(false) }
    var isMoreMenuOpen by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    TopAppBar(
        title = {
            if (isSearching) {
                TextField(
                    value = searchText,
                    onValueChange = { newText ->
                        searchText = newText
                        onSearch(searchText)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(text = "Search...") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Red,
                        focusedIndicatorColor = Color.Red,
                        unfocusedIndicatorColor =Color.Red
                    )
                )
            } else {
                Text(text = title)
            }
        },
        actions = {
            if (!isSearching) {
                IconButton(onClick = { isSearching = true }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                }
            } else {
                IconButton(onClick = {
                    isSearching = false
                    searchText = ""
                    onSearch("")
                }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Close Search"
                    )
                }
            }

            // Add Icon Button with Dropdown Menu
            IconButton(onClick = { isAddMenuOpen = true }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }

            DropdownMenu(
                expanded = isAddMenuOpen,
                onDismissRequest = { isAddMenuOpen = false }
            ) {
                DropdownMenuItem(
                    text = { Text(text = "New Category") },
                    onClick = {
                        isAddMenuOpen = false
                        addCategory(context)
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "New Item") },
                    onClick = {
                        isAddMenuOpen = false
                        addItem(context)
                                        }
                )
                DropdownMenuItem(
                    text = { Text(text = "New Shelf") },
                    onClick = {
                        isAddMenuOpen = false
                        addShelf(context)
                    }
                )

            }
            IconButton(onClick = { isMoreMenuOpen = true }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More Options"
                )
                DropdownMenu(
                    expanded = isMoreMenuOpen,
                    onDismissRequest = { isMoreMenuOpen = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(text = "Profile") },
                        onClick = {
                            isMoreMenuOpen = false
                            Toast.makeText(context, "Profile clicked", Toast.LENGTH_SHORT).show()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Filter") },
                        onClick = {
                            isMoreMenuOpen = false
                            Toast.makeText(context, "Filter clicked", Toast.LENGTH_SHORT).show()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Sort") },
                        onClick = {
                            isMoreMenuOpen = false
                            Toast.makeText(context, "Sort clicked", Toast.LENGTH_SHORT).show()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Log Out") },
                        onClick = {
                            isMoreMenuOpen = false
                            FirebaseAuth.getInstance().signOut()
                                navController.navigate(NavGraph.Login.route) {
                                    popUpTo(0) { inclusive = true }

                            }
                        }
                    )
                }
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}
fun onSearch(searchText: String) {
    /*TODO*/
}

fun addCategory(context: Context){
    Toast.makeText(context, "New Category clicked", Toast.LENGTH_SHORT).show()
}

fun addItem(context: Context){
    Toast.makeText(context, "New Item clicked", Toast.LENGTH_SHORT).show()
}

fun addShelf(context: Context)
{
    Toast.makeText(context, "New Shelf clicked", Toast.LENGTH_SHORT).show()
}

fun addPlace(newPlace: String)
{
    val userId= FirebaseAuth.getInstance().currentUser?.uid

        val db = userId?.let { FirebaseFirestore.getInstance().collection("users").document(it) }

       if(db!=null) {
           val docs = db.collection("places")
           docs.add(
               hashMapOf(
                   "names" to newPlace,
                   "description" to "New Place Description"
               )
           )
       }

    else
        Log.d("Chutiya kat gya", "Current User ID: $userId")
}





