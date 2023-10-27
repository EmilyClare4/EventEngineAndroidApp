package com.example.eventengine2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.eventengine2.data.Event;
import com.example.eventengine2.data.EventDatabase;

public class EventCreationActivity extends AppCompatActivity {
    private EditText titleEditText;
    private EditText descriptionEditText;
    private EditText costEditText;
    private EditText capacityEditText;
    private TextView catTextView;
    private EventDatabase eventDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creation);

        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        costEditText = findViewById(R.id.costEditText);
        capacityEditText = findViewById(R.id.capacityEditText);
        catTextView = findViewById(R.id.categoryTextView);
        String category = getIntent().getStringExtra("selectedCategory");
        catTextView.setText(category);

        Button createEventButton = findViewById(R.id.saveButton);
        createEventButton.setOnClickListener(v -> {
            String title = titleEditText.getText().toString();
            String description = descriptionEditText.getText().toString();
            double cost = Double.parseDouble(costEditText.getText().toString());
            int capacity = Integer.parseInt(capacityEditText.getText().toString());

            EventDatabase.runOnDatabaseExecutor(() -> {
                eventDatabase = EventDatabase.getDatabase(EventCreationActivity.this);
                long categoryId = eventDatabase.categoryDao().getCategory(category).getId();
                Event event = new Event(title, description, cost, capacity, categoryId);
                eventDatabase.eventDao().insert(event);
            });
            Toast.makeText(EventCreationActivity.this, "Event created successfully", Toast.LENGTH_SHORT).show();
            finish();
        });

        Button close = findViewById(R.id.closeButton);
        close.setOnClickListener(v -> finish());
    }
}
