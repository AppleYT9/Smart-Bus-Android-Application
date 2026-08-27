package com.example.smartbus.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartbus.R;
import com.example.smartbus.models.Notification;
import com.google.android.material.card.MaterialCardView;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private final List<Notification> notificationList;

    public NotificationAdapter(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = notificationList.get(position);
        
        holder.tvTitle.setText(notification.getTitle());
        holder.tvMessage.setText(notification.getMessage());
        holder.tvTime.setText(notification.getTime());

        // Toggle Read/Unread styling
        if (notification.isRead()) {
            holder.unreadDot.setVisibility(View.GONE);
            holder.cardNotification.setCardBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.surface));
            holder.tvTitle.setAlpha(0.6f);
            holder.tvMessage.setAlpha(0.7f);
        } else {
            holder.unreadDot.setVisibility(View.VISIBLE);
            holder.cardNotification.setCardBackgroundColor(0xFFF1F5F9); // Light slate/blue highlight
            holder.tvTitle.setAlpha(1.0f);
            holder.tvMessage.setAlpha(1.0f);
        }

        // Apply Icon configurations depending on Notification Type
        if ("warning".equalsIgnoreCase(notification.getType())) {
            holder.ivIcon.setImageResource(R.drawable.ic_notification);
            holder.ivIcon.setColorFilter(holder.itemView.getContext().getResources().getColor(R.color.error));
            holder.iconCard.setCardBackgroundColor(0xFFFEF2F2); // Soft Red
        } else if ("transit".equalsIgnoreCase(notification.getType())) {
            holder.ivIcon.setImageResource(R.drawable.ic_bus);
            holder.ivIcon.setColorFilter(holder.itemView.getContext().getResources().getColor(R.color.primary_light));
            holder.iconCard.setCardBackgroundColor(0xFFEFF6FF); // Soft Blue
        } else {
            holder.ivIcon.setImageResource(R.drawable.ic_route);
            holder.ivIcon.setColorFilter(holder.itemView.getContext().getResources().getColor(R.color.success));
            holder.iconCard.setCardBackgroundColor(0xFFF0FDF4); // Soft Green
        }

        // Tap item to toggle read state individually!
        holder.itemView.setOnClickListener(v -> {
            if (!notification.isRead()) {
                notification.setRead(true);
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    static class NotificationViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView cardNotification;
        MaterialCardView iconCard;
        ImageView ivIcon;
        View unreadDot;
        TextView tvTitle;
        TextView tvMessage;
        TextView tvTime;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            cardNotification = itemView.findViewById(R.id.card_notification);
            iconCard = itemView.findViewById(R.id.icon_card);
            ivIcon = itemView.findViewById(R.id.iv_notif_icon);
            unreadDot = itemView.findViewById(R.id.unread_dot);
            tvTitle = itemView.findViewById(R.id.tv_notif_title);
            tvMessage = itemView.findViewById(R.id.tv_notif_message);
            tvTime = itemView.findViewById(R.id.tv_notif_time);
        }
    }
}
