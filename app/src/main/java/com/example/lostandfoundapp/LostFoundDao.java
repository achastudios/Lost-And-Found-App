package com.example.lostandfoundapp;

// These imports are needed for Room queries
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

// This interface contains the database operations
@Dao
public interface LostFoundDao {

    // This inserts one item into the table
    @Insert
    void insert(LostFoundItem item);

    // This returns all items ordered newest first
    @Query("SELECT * FROM lost_found_items ORDER BY id DESC")
    List<LostFoundItem> getAllItems();

    // This returns items filtered by category
    @Query("SELECT * FROM lost_found_items WHERE category = :category ORDER BY id DESC")
    List<LostFoundItem> getItemsByCategory(String category);

    // This returns one item by id
    @Query("SELECT * FROM lost_found_items WHERE id = :id LIMIT 1")
    LostFoundItem getItemById(int id);

    // This deletes one item
    @Delete
    void delete(LostFoundItem item);
}