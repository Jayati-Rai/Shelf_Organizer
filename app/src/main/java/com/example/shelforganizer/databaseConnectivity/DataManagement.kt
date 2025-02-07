package com.example.shelforganizer.databaseConnectivity
import com.google.firebase.firestore.FirebaseFirestore

/*
THIS CAN BE IMPROVED BY INTRODUCING A NEW PARAMETER- COLLECTIONPATH
which directly paves a path and demands for the names
 */
fun fetchPlaceNames(
    userId: String, // The ID of the user document
    onSuccess: (List<String>,List<String>) -> Unit,
    onFailure: (Exception) -> Unit // Callback for errors
) {
    val db = FirebaseFirestore.getInstance().collection("users/$userId/places")
    // Reference to the "places" subcollection under the specific user document
    db.addSnapshotListener{snapshot, exception ->
        if (exception != null) {
            onFailure(exception)
            return@addSnapshotListener
        }
        if (snapshot != null && !snapshot.isEmpty) {
            val placenames=snapshot.documents.mapNotNull { it.getString("names") }
            val placeId=snapshot.documents.mapNotNull { it.id }
            onSuccess(placenames,placeId)
        } else {
            onSuccess(emptyList(),emptyList())
        }
    }
}
fun fetchItems(path:String="",
               onSuccess: (List<Item>) -> Unit,
               onFailure: (Exception) -> Unit){
    val db=FirebaseFirestore.getInstance().collection(path)
    db.addSnapshotListener { snapshot, exception ->
        if(exception!=null)
        {
            onFailure(exception)
            return@addSnapshotListener
        }
        if(snapshot!=null && !snapshot.isEmpty){
            val items=snapshot.documents.mapNotNull { it.toObject(Item::class.java) }
            onSuccess(items)
        }
        else{
            onSuccess(emptyList())
        }

    }

}
