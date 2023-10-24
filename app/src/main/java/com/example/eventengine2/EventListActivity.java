package com.example.eventengine2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.eventengine2.data.Event;
import com.example.eventengine2.data.EventDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class EventListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private EventDatabase eventDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        eventDatabase = EventDatabase.getDatabase(this);
        recyclerView = findViewById(R.id.recyclerViewEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String selectedCategory = getIntent().getStringExtra("selectedCategory");

        // DO I NEED THIS?? CHECK
        eventAdapter = new EventAdapter(new ArrayList<>(), this); // Initialize with an empty list
        recyclerView.setAdapter(eventAdapter);

        // Use an AsyncTask to perform database operations off the main thread
        new GetEventsAsyncTask().execute(selectedCategory);

        FloatingActionButton fabAddEvent = findViewById(R.id.fab);

        // Set a click listener for the FAB to open the event creation fragment
        fabAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("currentCategory", selectedCategory);
                openEventCreationFragment(bundle);
            }
        });
    }

    private class GetEventsAsyncTask extends AsyncTask<String, Void, List<Event>> {

        @Override
        protected List<Event> doInBackground(String... categories) {
            // Get all events within the selected category
            String selectedCategory = categories[0];
            Log.d("MyApp", "selectedCategory: " + selectedCategory);
            return eventDatabase.eventDao().getEventsByCategory(eventDatabase.categoryDao().getCategory(selectedCategory).getId());
        }

        @Override
        protected void onPostExecute(List<Event> events) {
            // Update the UI with the fetched events
            Log.d("MyApp", "eventList: " + events.size());
            for (Event event : events) {
                Log.d("MyApp", "Event ID: " + event.getId());
            }
            eventAdapter = new EventAdapter(events, EventListActivity.this);
            recyclerView.setAdapter(eventAdapter);
        }
    }

    private void openEventCreationFragment(Bundle bundle) {
        Log.d("MyApp", "Opening event creation fragment");
        // Create and show the event creation fragment
        EventCreationFragment fragment = new EventCreationFragment();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit();
    }
}
