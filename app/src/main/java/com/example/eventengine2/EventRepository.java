package com.example.eventengine2;

import android.util.Log;

import com.example.eventengine2.data.Event;
import com.example.eventengine2.data.EventDatabase;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventRepository {
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public void getEventsByCategory(String category, EventDatabase eventDatabase, Callback<List<Event>> callback) {
        Log.d("MyApp", "category in Event Repo is : " + category);
        executorService.execute(() -> {
            List<Event> events = eventDatabase.eventDao().getEventsByCategory(eventDatabase.categoryDao().getCategory(category).getId());
            callback.onComplete(events);
        });
    }

    interface Callback<T> {
        void onComplete(T result);
    }
}
