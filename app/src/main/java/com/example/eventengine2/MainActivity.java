package com.example.eventengine2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.eventengine2.data.Category;
import com.example.eventengine2.data.CategoryDao;
import com.example.eventengine2.data.Event;
import com.example.eventengine2.data.EventDao;
import com.example.eventengine2.data.EventDatabase;
import com.example.eventengine2.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EventDatabase eventDatabase;
    private CategoryDao categoryDao;
    private EventDao eventDao;
    private ArrayAdapter<String> adapter;
    public ActivityMainBinding mMainLayout;
    public int mSelectedCategory = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainLayout = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mMainLayout.getRoot());

        // Initialize the EventDatabase instance
        Log.d("MyApp", "Database context: " + this);
        eventDatabase = EventDatabase.getDatabase(this);
        categoryDao = eventDatabase.categoryDao();
        eventDao = eventDatabase.eventDao();

        // Replace this with actual category data retrieval logic
        getCategoryDataFromDatabase();
    }

    private void getCategoryDataFromDatabase() {
        EventDatabase.runOnDatabaseExecutor(() -> {
            List<Category> categories = categoryDao.getAllCategories();
            Log.d("MyApp", "Categories retrieved: " + categories.size());
            List<String> categoryNames = new ArrayList<>();
            // Add the "Select a category" as the initial item
            categoryNames.add("Select a category");
            for (Category category : categories) {
                categoryNames.add(category.getName());
            }
            mMainLayout.categorySpinner.post(() -> {
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categoryNames);
                mMainLayout.categorySpinner.setAdapter(adapter);
                mMainLayout.categorySpinner.setSelection(0); // Select the initial item
                mMainLayout.categorySpinner.setOnItemSelectedListener(this); // Set the listener
            });
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedCategory = parent.getItemAtPosition(position).toString();
        if (!selectedCategory.equals("Select a category")) {
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
}
