package com.example.eventengine2;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eventengine2.data.Event;
import com.example.eventengine2.data.EventDao;
import com.example.eventengine2.data.EventDatabase;

public class EventDetailsActivity extends AppCompatActivity {
    public static final String EXTRA_EVENT_ID = "event_id";
    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_item);

        int eventId = getIntent().getIntExtra(EXTRA_EVENT_ID, -1);
        if (eventId == -1) {
            // Handle invalid event ID
            // You can display an error message or return to the previous activity
            return;
        }

        EventDatabase database = EventDatabase.getDatabase(this);
        EventDao eventDao = database.eventDao();
        event = eventDao.getEventById(eventId);

        if (event == null) {
            // Handle event not found
            // You can display an error message or return to the previous activity
            return;
        }

        // Now, use the 'event' object to display event details in your UI
        TextView titleTextView = findViewById(R.id.titleTextView);
        TextView descriptionTextView = findViewById(R.id.descriptionTextView);
        // Bind other UI elements

        titleTextView.setText(event.getTitle());
        descriptionTextView.setText(event.getDescription());
        // Set other UI elements
    }
}

