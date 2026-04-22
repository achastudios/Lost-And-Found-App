package com.example.lostandfoundapp;

// These imports are needed for activity actions and image picking
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

// These imports are needed for AppCompatActivity and Room
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

// This activity lets the user create a new advert
public class AddAdvertActivity extends AppCompatActivity {

    // These variables connect to the XML views
    RadioGroup rgPostType;
    EditText etName, etPhone, etDescription, etDate, etLocation;
    Spinner spinnerCategory;
    ImageView ivSelectedImage;
    Button btnChooseImage, btnSave;

    // This stores the selected image URI as text
    String selectedImageUri = "";

    // This stores the Room database instance
    AppDatabase db;

    // This launcher opens the image picker and returns the chosen image
    ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri imageUri = result.getData().getData();
                    if (imageUri != null) {
                        selectedImageUri = imageUri.toString();
                        ivSelectedImage.setImageURI(imageUri);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // This runs when the activity starts
        super.onCreate(savedInstanceState);

        // This connects this Java file to activity_add_advert.xml
        setContentView(R.layout.activity_add_advert);

        // These lines connect Java variables to XML ids
        rgPostType = findViewById(R.id.rgPostType);
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etDescription = findViewById(R.id.etDescription);
        etDate = findViewById(R.id.etDate);
        etLocation = findViewById(R.id.etLocation);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        ivSelectedImage = findViewById(R.id.ivSelectedImage);
        btnChooseImage = findViewById(R.id.btnChooseImage);
        btnSave = findViewById(R.id.btnSave);

        // This creates the Room database
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "lost_found_db")
                .allowMainThreadQueries()
                .build();

        // This loads category values into the spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.category_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        // This fills the date field with the current date/time by default
        String currentDateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());
        etDate.setText(currentDateTime);

        // This opens the gallery image picker
        btnChooseImage.setOnClickListener(v -> {
            Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            imagePickerLauncher.launch(pickIntent);
        });

        // This saves the advert into the database
        btnSave.setOnClickListener(v -> {

            // This gets the selected post type id
            int selectedTypeId = rgPostType.getCheckedRadioButtonId();

            // This checks that a post type was selected
            if (selectedTypeId == -1) {
                Toast.makeText(this, "Please select Lost or Found", Toast.LENGTH_SHORT).show();
                return;
            }

            // These lines get the user input values
            RadioButton selectedTypeButton = findViewById(selectedTypeId);
            String postType = selectedTypeButton.getText().toString();
            String name = etName.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String description = etDescription.getText().toString().trim();
            String date = etDate.getText().toString().trim();
            String location = etLocation.getText().toString().trim();
            String category = spinnerCategory.getSelectedItem().toString();
            String timestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(new Date());

            // This checks that all text fields are filled
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(description)
                    || TextUtils.isEmpty(date) || TextUtils.isEmpty(location)) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // This checks that an image was chosen
            if (TextUtils.isEmpty(selectedImageUri)) {
                Toast.makeText(this, "Please choose an image", Toast.LENGTH_SHORT).show();
                return;
            }

            // This creates the item object
            LostFoundItem item = new LostFoundItem(
                    postType,
                    name,
                    phone,
                    description,
                    date,
                    location,
                    category,
                    selectedImageUri,
                    timestamp
            );

            // This inserts the item into Room
            db.lostFoundDao().insert(item);

            // This shows a success message
            Toast.makeText(this, "Advert saved", Toast.LENGTH_SHORT).show();

            // This closes the screen and returns to previous activity
            finish();
        });
    }
}