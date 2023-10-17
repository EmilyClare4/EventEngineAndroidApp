package com.example.eventengine2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventengine2.data.Event;
import com.example.eventengine2.data.EventDao;
import com.example.eventengine2.data.EventDatabase;
import java.util.ArrayList;
import java.util.List;

public class EventListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        EventDatabase db= EventDatabase.getDatabase(this);
        EventDao eventDao = db.eventDao();

        // Bind the RecyclerView from the layout
        recyclerView = findViewById(R.id.recyclerViewEvents);

        // Set up the RecyclerView's layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create and set the adapter to display event data
        eventAdapter = new EventAdapter(this); // Implement this method to retrieve event data
        recyclerView.setAdapter(eventAdapter);
        eventAdapter.setOnItemClickListener(new EventAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Event event) {
                // Retrieve the clicked event based on the position
                Intent intent = new Intent(EventListActivity.this, EventDetailsActivity.class);
                intent.putExtra(EventDetailsActivity.EXTRA_EVENT_ID, event.getId());
                startActivity(intent);
            }
        });

    }

    // Implement this method to retrieve event data
   /* private List<Event> getEventData() {
        List<Event> events = new ArrayList<>();
        // Example Event 1
        Event event1 = new Event();
        event1.setTitle("Event 1");
        event1.setDescription("Description for Event 1");
        // Set other attributes as needed
        events.add(event1);

        // Example Event 2
        Event event2 = new Event();
        event2.setTitle("Event 2");
        event2.setDescription("Description for Event 2");
        // Set other attributes as needed
        events.add(event2);

        // Example Event 3
        Event event3 = new Event();
        event3.setTitle("Event 3");
        event3.setDescription("Description for Event 3");
        // Set other attributes as needed
        events.add(event3);
        return events;
    }*/
}
