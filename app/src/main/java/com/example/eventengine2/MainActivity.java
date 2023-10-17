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
import com.example.eventengine2.data.EventDatabase;
import com.example.eventengine2.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EventDatabase eventDatabase;
    private CategoryDao categoryDao;
    private ArrayAdapter<String> adapter;
    public ActivityMainBinding mMainLayout;
    public int mSelectedCategory = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainLayout=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mMainLayout.getRoot());


        // Initialize the EventDatabase instance
        Log.d("MyApp", "Database context: " + this);
        eventDatabase = EventDatabase.getDatabase(this);
        categoryDao = eventDatabase.categoryDao();

        // Replace this with actual category data retrieval logic
        getCategoryDataFromDatabase();

        //adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //categorySpinner.setAdapter(adapter);

       /* categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        });*/
    }

    private void getCategoryDataFromDatabase() {
        EventDatabase.runOnDatabaseExecutor(() -> {
            List<Category> categories = categoryDao.getAllCategories();
            Log.d("MyApp", "Categories retrieved: " + categories.size());
            List<String> categoryNames = new ArrayList<>();
            for (Category category : categories) {
                categoryNames.add(category.getName());
            }
            mMainLayout.categorySpinner.post(() -> {
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categoryNames);
                mMainLayout.categorySpinner.setAdapter(adapter);
                mMainLayout.categorySpinner.setSelection(mSelectedCategory);
                mMainLayout.categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selectedCategory = categoryNames.get(position);
                        if (!selectedCategory.equals("Select a category from the list")) {
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
            });
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //updateCategoryList();
        Toast.makeText(MainActivity.this, "Please select a category.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }




       /* // Use AsyncTask to retrieve categories from the Category table
        new AsyncTask<Void, Void, List<Category>>() {
            @Override
            protected List<Category> doInBackground(Void... voids) {
                return eventDatabase.
            }

            @Override
            protected void onPostExecute(List<Category> categoryList) {
                for (Category category : categoryList) {
                    categories.add(category.getName());
                }

                // Update the spinner adapter when data is available
                adapter.notifyDataSetChanged();
            }
        }.execute();

        return categories;
    }*/
}
