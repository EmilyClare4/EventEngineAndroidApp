package com.example.eventengine2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.eventengine2.data.Event;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private Context context;
    private List<Event> eventList;
    private String selectedCategory;

    public EventAdapter(List<Event> events, Context context, String selectedCategory) {
        this.eventList = events;
        this.context = context;
        this.selectedCategory = selectedCategory;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.event_list_item, parent, false);
        return new EventViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.bind(event);

        // Set a click listener for the item view
        holder.itemView.setOnClickListener(v -> {
            // Start the EventDetailActivity and pass the event details
            Intent intent = new Intent(context, EventDetailActivity.class);
            intent.putExtra("event", event);
            intent.putExtra("selectedCategory", selectedCategory);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
        }

        public void bind(Event event) {
            titleTextView.setText(event.getTitle());
        }
    }
}
