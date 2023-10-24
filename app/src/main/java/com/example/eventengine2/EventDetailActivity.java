package com.example.eventengine2;

import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.eventengine2.data.Event;

public class EventDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_item);

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
            costTextView.setText(String.format("%.2f", event.getCost()));
            capacityTextView.setText(Integer.toString(event.getCapacity()));
            //categoryTextView.setText(event.getCategoryId());
        }
    }
}
