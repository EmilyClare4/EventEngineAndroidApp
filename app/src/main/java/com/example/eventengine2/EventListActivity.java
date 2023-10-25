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

    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private EventDatabase eventDatabase;
    private String selectedCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        eventDatabase = EventDatabase.getDatabase(this);
        recyclerView = findViewById(R.id.recyclerViewEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        selectedCategory = getIntent().getStringExtra("selectedCategory");

        // Use an AsyncTask to perform database operations off the main thread
        new GetEventsAsyncTask().execute(selectedCategory);

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

    private class GetEventsAsyncTask extends AsyncTask<String, Void, List<Event>> {

        @Override
        protected List<Event> doInBackground(String... categories) {
            // Get all events within the selected category
            selectedCategory = categories[0];
            return eventDatabase.eventDao().getEventsByCategory(eventDatabase.categoryDao().getCategory(selectedCategory).getId());
        }

        protected void onPostExecute(List<Event> events) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Update the UI with the fetched events on the main UI thread
                    eventAdapter = new EventAdapter(events, EventListActivity.this, selectedCategory);
                    recyclerView.setAdapter(eventAdapter);
                }
            });
        }
    }
}
