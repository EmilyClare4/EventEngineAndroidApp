package com.example.eventengine2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.eventengine2.data.EventDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


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
        fabAddEvent.setOnClickListener(view -> {
            Intent intent = new Intent(EventListActivity.this, EventCreationActivity.class);
            intent.putExtra("selectedCategory", selectedCategory);
            startActivity(intent);
        });

        Button close = findViewById(R.id.homeButton);
        close.setOnClickListener(v -> finish());
    }

    private void updateEventList(String category) {
        eventRepository.getEventsByCategory(category, eventDatabase, events -> runOnUiThread(() -> {
            eventAdapter = new EventAdapter(events, EventListActivity.this, category);
            recyclerView.setAdapter(eventAdapter);
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateEventList(selectedCategory);
    }
}
