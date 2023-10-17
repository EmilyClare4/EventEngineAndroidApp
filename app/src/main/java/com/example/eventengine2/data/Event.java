package com.example.eventengine2.data;

import androidx.room.*;

@Entity(tableName = "events", foreignKeys = @ForeignKey(entity = Category.class,
        parentColumns = "category_id",
        childColumns = "category_id",
        onDelete = ForeignKey.CASCADE))
public class Event {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "event_id")
    private int event_id;
    private String title;
    private String description;
    private String location;
    private double cost;
    private int capacity;
    private boolean isIndoors;
    @ColumnInfo(name = "category_id")
    private int categoryId;
    Event(String title, String description, String location, double cost, int capacity, boolean isIndoors, int categoryId) {
        this.title=title;
        this.description=description;
        this.location=location;
        this.cost=cost;
        this.capacity=capacity;
        this.isIndoors=isIndoors;
        this.categoryId=categoryId;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isIndoors() {
        return isIndoors;
    }

    public void setIndoors(boolean indoors) {
        isIndoors = indoors;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

}

