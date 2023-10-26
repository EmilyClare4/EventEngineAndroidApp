package com.example.eventengine2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.eventengine2.data.Event;
import com.example.eventengine2.data.EventDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class EventListActivity extends AppCompatActivity  {

    private static RecyclerView recyclerView;
    private static EventAdapter eventAdapter;
    private static EventDatabase eventDatabase;
    private EventRepository eventRepository;
    private String selectedCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        eventDatabase = EventDatabase.getDatabase(this);
        recyclerView = findViewById(R.id.recyclerViewEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        selectedCategory = getIntent().getStringExtra("selectedCategory");

        eventRepository = new EventRepository();

        updateEventList(selectedCategory);

        FloatingActionButton fabAddEvent = findViewById(R.id.fab);
        // Set a click listener for the FAB to open the event creation fragment
        fabAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the EventCreationActivity and pass the event details
                Intent intent = new Intent(EventListActivity.this, EventCreationActivity.class);
                intent.putExtra("selectedCategory", selectedCategory);
                startActivity(intent);
            }
        });
    }

    private void updateEventList(String category) {
        eventRepository.getEventsByCategory(category, eventDatabase, new EventRepository.Callback<List<Event>>() {
            @Override
            public void onComplete(List<Event> events) {
                runOnUiThread(() -> {
                    eventAdapter = new EventAdapter(events, EventListActivity.this, category);
                    recyclerView.setAdapter(eventAdapter);
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateEventList(selectedCategory);
    }
}
