package com.example.eventengine2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eventengine2.data.Category;
import com.example.eventengine2.data.CategoryDao;
import com.example.eventengine2.data.Event;
import com.example.eventengine2.data.EventDao;
import com.example.eventengine2.data.EventDatabase;

public class EventCreationActivity extends AppCompatActivity {
    private EditText titleEditText;
    private EditText descriptionEditText;
    private EditText costEditText;
    private EditText capacityEditText;
    private Spinner categorySpinner;
    private EventDatabase eventDatabase = EventDatabase.getDatabase(this);
    private CategoryDao categoryDao = eventDatabase.categoryDao();
    private EventDao eventDao = eventDatabase.eventDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creation);

        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        costEditText = findViewById(R.id.costEditText);
        capacityEditText = findViewById(R.id.capacityEditText);
        categorySpinner = findViewById(R.id.categorySpinner);

        Button createEventButton = findViewById(R.id.saveButton);

        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEditText.getText().toString();
                String description = descriptionEditText.getText().toString();
                double cost = Double.parseDouble(costEditText.getText().toString());
                int capacity = Integer.parseInt(capacityEditText.getText().toString());
                //String selectedCategoryName = categorySpinner.getSelectedItem().toString();

                // Find the Category by name
                //Category selectedCategory = categoryDao.getCategory(selectedCategoryName);
                Event event = new Event(title, description, cost, capacity, 2);

                EventDatabase.runOnDatabaseExecutor(() -> {
                    // Insert the event into the database
                    eventDao.insert(event);
                });



                // Provide feedback to the user
                Toast.makeText(EventCreationActivity.this, "Event created successfully", Toast.LENGTH_SHORT).show();
                finish();

                /*if (selectedCategory != null) {
                    // Create a new Event
                    Event event = new Event(title, description, cost, capacity, 2);

                    // Insert the event into the database
                    eventDao.insert(event);

                    // Provide feedback to the user
                    Toast.makeText(EventCreationActivity.this, "Event created successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EventCreationActivity.this, "Invalid category selected", Toast.LENGTH_SHORT).show();
                }*/
            }
        });
    }
}
