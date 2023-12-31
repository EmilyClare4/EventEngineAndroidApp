package com.example.eventengine2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.eventengine2.data.Event;
import com.example.eventengine2.data.EventDatabase;

public class EventDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        // Get the event details from the intent
        Event event = (Event) getIntent().getSerializableExtra("event");

        // Display event details in the activity
        if (event != null) {

            TextView titleTextView = findViewById(R.id.titleTextView);
            TextView descriptionTextView = findViewById(R.id.descriptionTextView);
            TextView costTextView = findViewById(R.id.costTextView);
            TextView capacityTextView = findViewById(R.id.capacityTextView);
            TextView categoryTextView = findViewById(R.id.categoryTextView);

            titleTextView.setText(event.getTitle());
            descriptionTextView.setText(event.getDescription());
            costTextView.setText(String.format("$%.2f", event.getCost()));
            capacityTextView.setText(event.getCapacity() + " people");
            categoryTextView.setText(getIntent().getStringExtra("selectedCategory"));

            Button deleteButton = findViewById(R.id.deleteButton);
            deleteButton.setOnClickListener(view -> {
                // Delete the event from the database
                EventDatabase.runOnDatabaseExecutor(() -> {
                    EventDatabase eventDatabase = EventDatabase.getDatabase(EventDetailActivity.this);
                    eventDatabase.eventDao().deleteEvent(event.getId());
                });
                Toast.makeText(EventDetailActivity.this, "Event deleted successfully", Toast.LENGTH_SHORT).show();
                finish();
            });

            Button close = findViewById(R.id.closeButton);
            close.setOnClickListener(v -> finish());
        }
    }
}
