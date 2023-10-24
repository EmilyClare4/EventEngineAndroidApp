package com.example.eventengine2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.eventengine2.data.Event;
import com.example.eventengine2.data.EventDatabase;

public class EventCreationFragment extends Fragment {
    private EditText titleEditText;
    private EditText descriptionEditText;
    private EditText costEditText;
    private EditText capacityEditText;
    private TextView catTextView;
    private EventDatabase eventDatabase;
    private EventAdapter eventAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_event_creation, container, false);

        titleEditText = view.findViewById(R.id.titleEditText);
        descriptionEditText = view.findViewById(R.id.descriptionEditText);
        costEditText = view.findViewById(R.id.costEditText);
        capacityEditText = view.findViewById(R.id.capacityEditText);
        catTextView = view.findViewById(R.id.catTextView);

        Button createEventButton = view.findViewById(R.id.saveButton);

        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEditText.getText().toString();
                String description = descriptionEditText.getText().toString();
                double cost = Double.parseDouble(costEditText.getText().toString());
                int capacity = Integer.parseInt(capacityEditText.getText().toString());


                // Adjust the event creation logic as needed
                Event event = new Event(title, description, cost, capacity, 2);

                // Access the database and insert the event
                eventDatabase = EventDatabase.getDatabase(requireContext());
                EventDatabase.runOnDatabaseExecutor(() -> {
                            eventDatabase.eventDao().insert(event);
                    eventAdapter.addEvent(event);  // Add the new event to the adapter's dataset
                    eventAdapter.notifyDataSetChanged();
                });

                // Provide feedback to the user
                Toast.makeText(requireContext(), "Event created successfully", Toast.LENGTH_SHORT).show();
                //getActivity().getSupportFragmentManager().beginTransaction().remove(EventCreationFragment.this).commit();
            }
        });

        return view;
    }
}
