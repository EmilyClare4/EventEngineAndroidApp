package com.example.eventengine2.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface EventDao {
    @Insert
    long insert(Event event);

    @Query("SELECT * FROM events WHERE id = :categoryId")
    List<Event> getEventsByCategory(int categoryId);

    @Query("SELECT * FROM events WHERE id = :eventId")
    Event getEventById(int eventId);

    // Add other queries as needed
}


