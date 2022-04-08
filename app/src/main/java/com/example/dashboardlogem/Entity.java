package com.example.dashboardlogem;

import androidx.room.PrimaryKey;

@androidx.room.Entity(tableName = "table")
public class Entity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;

    public Entity(String title, String description) {
        this.title = title;
        this.description = description;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
