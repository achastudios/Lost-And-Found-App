package com.example.lostandfoundapp;

// These imports are needed for Room database class
import androidx.room.Database;
import androidx.room.RoomDatabase;

// This class defines the Room database
@Database(entities = {LostFoundItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    // This method gives access to the DAO
    public abstract LostFoundDao lostFoundDao();
}