package com.example.lostandfoundapp;

// These imports are needed for Room annotations
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// This class becomes the database table
@Entity(tableName = "lost_found_items")
public class LostFoundItem {

    // This is the auto-generated primary key
    @PrimaryKey(autoGenerate = true)
    public int id;

    // These fields store the advert values
    public String postType;
    public String name;
    public String phone;
    public String description;
    public String date;
    public String location;
    public String category;
    public String imageUri;
    public String timestamp;

    // This constructor sets the item values
    public LostFoundItem(String postType, String name, String phone, String description,
                         String date, String location, String category, String imageUri,
                         String timestamp) {
        this.postType = postType;
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.date = date;
        this.location = location;
        this.category = category;
        this.imageUri = imageUri;
        this.timestamp = timestamp;
    }
}