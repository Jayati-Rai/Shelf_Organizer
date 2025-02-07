package com.example.shelforganizer.authentication

import androidx.navigation.NavController
import com.example.shelforganizer.navigation.NavGraph
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

fun loginUser(email: String, password: String, onSuccess:()->Unit, onFailure:(String)->Unit){
 val auth =FirebaseAuth.getInstance()
 auth.signInWithEmailAndPassword(email,password)
  .addOnCompleteListener{task->
   val userId=task.result?.user?.uid
   if(task.isSuccessful){
    onSuccess()
   }
   else
   {
    onFailure(task.exception?.message?:"Login failed")
   }
 }
}



 fun registerUser(username:String, email:String, password: String, navController: NavController) {
 val auth = FirebaseAuth.getInstance()
  var  errorMessage:String?=""
   try {
    auth.createUserWithEmailAndPassword(email, password)
     .addOnCompleteListener { task ->
      if (task.isSuccessful) {
       navController.navigate(NavGraph.Dashboard.route) {
        popUpTo(NavGraph.SignUp.route) { inclusive = true }
        createUserDocumentIfNotExists(username, email, auth)
       }
      } else {
        errorMessage = task.exception?.localizedMessage
      }
     }
   } catch (e: Exception) {
     errorMessage = e.localizedMessage
   }
  }
//to create a document in the firebase database if it doesn't exist
fun createUserDocumentIfNotExists(username: String, email: String, auth: FirebaseAuth) {
 val db = FirebaseFirestore.getInstance()
 val user = auth.currentUser
 user?.let { it ->
  val userId = it.uid
  val docRef = db.collection("users").document(userId)
  docRef.get().addOnSuccessListener { documentSnapshot ->
   if (!documentSnapshot.exists()) {
    val newUserMap = hashMapOf(
     "username" to username,
     "email" to email
    )
    docRef.set(newUserMap).addOnSuccessListener {
     println("Document created Successfully!")
    }
     .addOnFailureListener{
      println("Error creating document: ${it.localizedMessage}")
     }
   }
   else{
    println("User document already exists!")
   }
  }
   .addOnFailureListener{
    println("Error getting document: ${it.localizedMessage}")
   }
 }
}


