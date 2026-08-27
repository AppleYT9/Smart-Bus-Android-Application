package com.example.smartbus.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartbus.BusDetailsActivity;
import com.example.smartbus.R;
import com.example.smartbus.models.Route;
import java.util.List;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.RouteViewHolder> {

    private final List<Route> routeList;

    public RouteAdapter(List<Route> routeList) {
        this.routeList = routeList;
    }

    @NonNull
    @Override
    public RouteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_route, parent, false);
        return new RouteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RouteViewHolder holder, int position) {
        Route route = routeList.get(position);
        holder.tvBusNumber.setText(route.getBusNumber());
        holder.tvRouteName.setText(route.getRouteName());
        
        int stopsSize = route.getStopsList() != null ? route.getStopsList().size() : 0;
        holder.tvStopsCount.setText(stopsSize + (stopsSize == 1 ? " stop" : " stops"));
        holder.tvStatus.setText(route.getStatus());

        holder.btnViewDetails.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, BusDetailsActivity.class);
            intent.putExtra("selected_route", route);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return routeList.size();
    }

    static class RouteViewHolder extends RecyclerView.ViewHolder {
        TextView tvBusNumber;
        TextView tvStatus;
        TextView tvRouteName;
        TextView tvStopsCount;
        View btnViewDetails;

        public RouteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBusNumber = itemView.findViewById(R.id.tv_bus_number);
            tvStatus = itemView.findViewById(R.id.tv_status);
            tvRouteName = itemView.findViewById(R.id.tv_route_name);
            tvStopsCount = itemView.findViewById(R.id.tv_stops_count);
            btnViewDetails = itemView.findViewById(R.id.btn_view_details);
        }
    }
}
