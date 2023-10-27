package com.example.eventengine2.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface EventDao {
    @Insert
    long insert(Event event);

    @Query("SELECT * FROM events")
    List<Event> getAllEvents();

    @Query("SELECT * FROM events WHERE category_id = :categoryId")
    List<Event> getEventsByCategory(long categoryId);

    @Query("SELECT * FROM events WHERE id = :eventId")
    Event getEventById(long eventId);

    @Query("DELETE FROM events WHERE id=:id")
    void deleteEvent(long id);
}


