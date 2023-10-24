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

    public EventAdapter(List<Event> events, Context context) {
        this.eventList = events;
        this.context = context;
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the EventDetailActivity and pass the event details
                Intent intent = new Intent(context, EventDetailActivity.class);
                intent.putExtra("event", event);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public void addEvent(Event event) {
        eventList.add(event);
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        //private TextView descriptionTextView;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            //descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
        }

        public void bind(Event event) {
            titleTextView.setText(event.getTitle());
            //descriptionTextView.setText(event.getDescription());
        }
    }
}
