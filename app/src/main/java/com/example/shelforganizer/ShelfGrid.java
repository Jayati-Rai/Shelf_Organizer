package com.example.shelforganizer;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ShelfGrid extends AppCompatActivity {
    private static final String TAG="SHELF GRID!!!!!!!";


    GridView var_gridView;
    ArrayList<ShelfModel> shelfModelArrayList;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shelf_grid);

        var_gridView = findViewById(R.id.gridview);
        shelfModelArrayList = new ArrayList<>(20);

        //initializing firebasefirestore database
        db=FirebaseFirestore.getInstance();
        loadDatainGridView();




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void loadDatainGridView() {
        db.collection("Data").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {
                            // Convert the Firestore document to a ShelfModel object
                            ShelfModel shelfModel = d.toObject(ShelfModel.class);
                            if (shelfModel != null) {
                                // Check if shelf_name is missing and handle accordingly
                                if (shelfModel.getShelf_name() == null) {
                                    // Handle case where shelf_name is missing or null
                                    Log.w(TAG, "Shelf name is null for document ID: " + d.getId());
                                    shelfModel.setShelf_name("Default Shelf Name"); // Provide a default value
                                }

                                shelfModel.setImageID(R.drawable.shelfimg); // Default image
                                Log.d(TAG, "Shelf name: " + shelfModel.getShelf_name());
                                shelfModelArrayList.add(shelfModel); // Add the ShelfModel object to the list
                            } else {
                                Log.w(TAG, "Failed to convert DocumentSnapshot to ShelfModel!");
                            }
                        }
                        // Create and set the adapter for the GridView
                        ShelfGVAdapter adapter = new ShelfGVAdapter(ShelfGrid.this, shelfModelArrayList);
                        var_gridView.setAdapter(adapter);
                    } else {
                        Toast.makeText(ShelfGrid.this, "No data found in database.", Toast.LENGTH_SHORT).show();
                        Log.w(TAG, "No data found in database.");
                    }
                }).addOnFailureListener(e -> {
                    Toast.makeText(ShelfGrid.this, "Fail to load data..", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Error loading data", e);
                });
    }

}