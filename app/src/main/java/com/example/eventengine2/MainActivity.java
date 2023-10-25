package com.example.eventengine2;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import com.example.eventengine2.data.Category;
import com.example.eventengine2.data.CategoryDao;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainLayout = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mMainLayout.getRoot());

        // Initialize the EventDatabase instance
        eventDatabase = EventDatabase.getDatabase(this);
        categoryDao = eventDatabase.categoryDao();
        eventDao = eventDatabase.eventDao();

        getCategoryDataFromDatabase();
    }

    private void getCategoryDataFromDatabase() {
        EventDatabase.runOnDatabaseExecutor(() -> {
            List<Category> categories = categoryDao.getAllCategories();
            List<String> categoryNames = new ArrayList<>();
            // Add "Select a category" as the initial item
            categoryNames.add("Select a category");
            for (Category category : categories) {
                categoryNames.add(category.getName());
            }
            mMainLayout.categorySpinner.post(() -> {
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categoryNames);
                mMainLayout.categorySpinner.setAdapter(adapter);
                mMainLayout.categorySpinner.setSelection(0);
                mMainLayout.categorySpinner.setOnItemSelectedListener(this);
            });
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedCategory = parent.getItemAtPosition(position).toString();
        if (!selectedCategory.equals("Select a category")) {
            Intent intent = new Intent(MainActivity.this, EventListActivity.class);
            intent.putExtra("selectedCategory", selectedCategory);
            startActivity(intent);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
