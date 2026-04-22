package com.example.lostandfoundapp;

// These imports are needed for activity actions
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

// These imports are needed for AppCompatActivity and Room
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

// This activity shows all saved lost and found items
public class ItemListActivity extends AppCompatActivity {

    // These variables connect to the XML views
    Spinner spinnerFilter;
    ListView listViewItems;

    // These variables store the database and list data
    AppDatabase db;
    ArrayList<LostFoundItem> itemObjects;
    ItemListAdapter itemListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // This runs when the activity starts
        super.onCreate(savedInstanceState);

        // This connects this Java file to activity_item_list.xml
        setContentView(R.layout.activity_item_list);

        // These lines connect Java variables to XML ids
        spinnerFilter = findViewById(R.id.spinnerFilter);
        listViewItems = findViewById(R.id.listViewItems);

        // This creates the Room database
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "lost_found_db")
                .allowMainThreadQueries()
                .build();

        // This loads category filter values into the spinner
        ArrayAdapter<CharSequence> filterAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.filter_array,
                android.R.layout.simple_spinner_item
        );
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFilter.setAdapter(filterAdapter);

        // This reloads the list whenever the filter changes
        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                loadItems();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // This opens the detail screen when a list item is clicked
        listViewItems.setOnItemClickListener((parent, view, position, id) -> {
            LostFoundItem selectedItem = itemObjects.get(position);
            Intent intent = new Intent(ItemListActivity.this, ItemDetailActivity.class);
            intent.putExtra("item_id", selectedItem.id);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {

        // This reloads the list when coming back to this screen
        super.onResume();
        loadItems();
    }

    // This method loads items from the database into the list view
    private void loadItems() {

        // This gets the selected filter value
        String selectedFilter = spinnerFilter.getSelectedItem().toString();

        // This gets items based on filter
        if (selectedFilter.equals("All")) {
            itemObjects = new ArrayList<>(db.lostFoundDao().getAllItems());
        } else {
            itemObjects = new ArrayList<>(db.lostFoundDao().getItemsByCategory(selectedFilter));
        }

        // This creates the custom adapter and attaches it to the list
        itemListAdapter = new ItemListAdapter(this, itemObjects);
        listViewItems.setAdapter(itemListAdapter);
    }
}