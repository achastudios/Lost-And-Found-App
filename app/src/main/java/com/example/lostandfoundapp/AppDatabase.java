package com.example.lostandfoundapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {LostFoundItem.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract LostFoundDao lostFoundDao();
}