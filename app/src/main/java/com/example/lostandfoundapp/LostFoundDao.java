package com.example.lostandfoundapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface LostFoundDao {

    @Insert
    void insert(LostFoundItem item);

    @Query("SELECT * FROM lost_found_items ORDER BY id DESC")
    List<LostFoundItem> getAllItems();

    @Query("SELECT * FROM lost_found_items WHERE category = :category ORDER BY id DESC")
    List<LostFoundItem> getItemsByCategory(String category);

    @Query("SELECT * FROM lost_found_items WHERE id = :id LIMIT 1")
    LostFoundItem getItemById(int id);

    @Delete
    void delete(LostFoundItem item);
}