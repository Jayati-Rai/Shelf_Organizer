package com.example.shelforganizer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button button_1;
    private LinearLayout buttonContainer;//layout that contains button

    ImageButton imgButton;
    Intent intent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        initialize();

        intent =new Intent(this, ShelfGrid.class);
        onclick();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void onclick() {
    button_1.setOnClickListener(v -> startActivity(intent));
    //TODO - on click a new button should be there
        imgButton.setOnClickListener(v -> showAddButtonDialogue());

    }

    private void showAddButtonDialogue() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        LayoutInflater inflater =getLayoutInflater();
        View dialogueView=inflater.inflate(R.layout.dialouge_add_button,null);
        builder.setView(dialogueView);

        EditText input= dialogueView.findViewById(R.id.pname_edit_textview);

        AlertDialog dialog=builder.create();
        Button createButton= dialogueView.findViewById(R.id.button_create);
if(createButton!=null) {
    createButton.setOnClickListener(v -> {
        String buttonName = input.getText().toString().trim();
        if (!buttonName.isEmpty()) {
            createNewButton(buttonName);
            dialog.dismiss();
        } else {
            Toast.makeText(MainActivity.this, "Please enter a name. ", Toast.LENGTH_SHORT).show();
        }
    });
    dialog.show();
}
else
{
    Toast.makeText(MainActivity.this, "Button value is null", Toast.LENGTH_SHORT).show();
}

    }

    private void createNewButton(String buttonName) {
    Button newbutton=new Button(this);
    newbutton.setText(buttonName);
    newbutton.setId(View.generateViewId());
    buttonContainer.addView(newbutton);

    newbutton.setOnClickListener(v->startActivity(intent));

    }


    private void initialize() {
        button_1=findViewById(R.id.button1);
        imgButton=findViewById(R.id.addButton);
        buttonContainer=findViewById(R.id.button_container);
    }
}