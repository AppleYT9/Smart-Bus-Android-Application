package com.example.smartbus.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartbus.R;
import com.example.smartbus.models.Stop;
import java.util.List;

public class StopAdapter extends RecyclerView.Adapter<StopAdapter.StopViewHolder> {

    private final List<Stop> stopList;

    public StopAdapter(List<Stop> stopList) {
        this.stopList = stopList;
    }

    @NonNull
    @Override
    public StopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stop, parent, false);
        return new StopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StopViewHolder holder, int position) {
        Stop stop = stopList.get(position);
        holder.stopName.setText(stop.getName());
        holder.arrivalTime.setText(stop.getArrivalTime());

        if (stop.isPassed()) {
            holder.timelineDot.setBackgroundResource(R.drawable.dot_passed);
            holder.stopName.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.text_secondary));
            holder.stopName.setAlpha(0.6f);
        } else {
            holder.timelineDot.setBackgroundResource(R.drawable.dot_upcoming);
            holder.stopName.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.text_primary));
            holder.stopName.setAlpha(1.0f);
        }
    }

    @Override
    public int getItemCount() {
        return stopList.size();
    }

    static class StopViewHolder extends RecyclerView.ViewHolder {
        View timelineDot;
        TextView stopName;
        TextView arrivalTime;

        public StopViewHolder(@NonNull View itemView) {
            super(itemView);
            timelineDot = itemView.findViewById(R.id.timeline_dot);
            stopName = itemView.findViewById(R.id.stop_name);
            arrivalTime = itemView.findViewById(R.id.arrival_time);
        }
    }
}
