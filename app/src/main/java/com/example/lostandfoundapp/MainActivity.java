package com.example.lostandfoundapp;

// These imports are needed for activity actions
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

// This import is needed for AppCompatActivity
import androidx.appcompat.app.AppCompatActivity;

// This is the first screen of the app
public class MainActivity extends AppCompatActivity {

    // These variables connect to the XML buttons
    Button btnCreateAdvert, btnShowItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // This runs when the activity starts
        super.onCreate(savedInstanceState);

        // This connects this Java file to activity_main.xml
        setContentView(R.layout.activity_main);

        // These lines connect Java variables to XML ids
        btnCreateAdvert = findViewById(R.id.btnCreateAdvert);
        btnShowItems = findViewById(R.id.btnShowItems);

        // This opens the create advert screen
        btnCreateAdvert.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddAdvertActivity.class);
            startActivity(intent);
        });

        // This opens the list screen
        btnShowItems.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ItemListActivity.class);
            startActivity(intent);
        });
    }
}