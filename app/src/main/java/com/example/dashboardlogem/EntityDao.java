package com.example.dashboardlogem;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EntityDao {

    @Insert
    void insert(Entity entity);

    @Update
    void update(Entity entity);

    @Delete
    void delete(Entity entity);

    @Query("DELETE FROM `table`")
    void deleteAllNotes();

    @Query("SELECT * FROM `table`")
    LiveData<List<Entity>> getAllNotes();
}
