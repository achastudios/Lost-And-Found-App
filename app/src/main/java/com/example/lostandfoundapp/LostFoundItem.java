package com.example.lostandfoundapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "lost_found_items")
public class LostFoundItem {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String postType;
    public String name;
    public String phone;
    public String description;
    public String date;
    public String location;
    public String category;
    public String imageUri;
    public String timestamp;

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