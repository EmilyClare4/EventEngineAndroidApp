package com.example.eventengine2.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface EventDao {
    @Insert
    void insertEvent(Event event);

    @Query("SELECT * FROM events WHERE category_id = :categoryId")
    List<Event> getEventsByCategory(int categoryId);

    @Query("SELECT * FROM events WHERE event_id = :eventId")
    Event getEventById(int eventId);

    // Add other queries as needed
}


