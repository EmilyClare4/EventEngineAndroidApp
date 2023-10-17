package com.example.eventengine2;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.eventengine2.data.EventDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner categorySpinner = findViewById(R.id.categorySpinner);

        // Replace this with actual category data retrieval logic
        List<String> categories = getCategoryDataFromDatabase();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categorySpinner.setAdapter(adapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle the selected category here
                String selectedCategory = (String) parentView.getItemAtPosition(position);
                if (selectedCategory != "Select a category from the list") {
                    // Create an Intent to start the EventListActivity
                    Intent intent = new Intent(MainActivity.this, EventListActivity.class);
                    intent.putExtra("selectedCategory", selectedCategory);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Please select a category.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private List<String> getCategoryDataFromDatabase() {
        List<String> categories = new ArrayList<>();
        // Populate categories from database or another source
        categories.add("Select a category from the list");
        categories.add("Category 2");
        categories.add("Category 3");
        return categories;
    }
}
