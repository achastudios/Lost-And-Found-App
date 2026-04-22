package com.example.lostandfoundapp;

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
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddAdvertActivity extends AppCompatActivity {

    RadioGroup rgPostType;
    EditText etName, etPhone, etDescription, etDate, etLocation;
    Spinner spinnerCategory;
    ImageView ivSelectedImage;
    Button btnChooseImage, btnSave;

    String selectedImageUri = "";
    AppDatabase db;

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_advert);

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

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "lost_found_db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.category_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        String currentDateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());
        etDate.setText(currentDateTime);

        btnChooseImage.setOnClickListener(v -> {
            Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            imagePickerLauncher.launch(pickIntent);
        });

        btnSave.setOnClickListener(v -> {

            int selectedTypeId = rgPostType.getCheckedRadioButtonId();

            if (selectedTypeId == -1) {
                Toast.makeText(this, "Please select Lost or Found", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selectedTypeButton = findViewById(selectedTypeId);
            String postType = selectedTypeButton.getText().toString();
            String name = etName.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String description = etDescription.getText().toString().trim();
            String date = etDate.getText().toString().trim();
            String location = etLocation.getText().toString().trim();
            String category = spinnerCategory.getSelectedItem().toString();
            String timestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(new Date());

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(description)
                    || TextUtils.isEmpty(date) || TextUtils.isEmpty(location)) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(selectedImageUri)) {
                Toast.makeText(this, "Please choose an image", Toast.LENGTH_SHORT).show();
                return;
            }

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

            db.lostFoundDao().insert(item);

            Toast.makeText(this, "Advert saved", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}