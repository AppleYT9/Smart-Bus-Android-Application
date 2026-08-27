package com.example.smartbus.models;

import java.util.ArrayList;
import java.util.List;

public class NotificationRepository {
    private static NotificationRepository instance;
    private final List<Notification> notificationList;

    private NotificationRepository() {
        notificationList = new ArrayList<>();
        // Initialize default mock alerts
        notificationList.add(new Notification(1, "Bus SB-101", "Bus SB-101 is approaching Main Road.", "2 mins ago", false, "transit"));
        notificationList.add(new Notification(2, "Bus SB-102 Alert", "Bus SB-102 is delayed by 10 minutes.", "15 mins ago", false, "warning"));
        notificationList.add(new Notification(3, "Route SB-103", "Route SB-103 is operating normally.", "1 hour ago", true, "info"));
        notificationList.add(new Notification(4, "Bus SB-101", "Bus SB-101 has reached City Mall.", "2 hours ago", true, "transit"));
    }

    public static synchronized NotificationRepository getInstance() {
        if (instance == null) {
            instance = new NotificationRepository();
        }
        return instance;
    }

    public List<Notification> getNotifications() {
        return notificationList;
    }

    public void addNotification(Notification notification) {
        // Add to index 0 so new notifications appear at the top!
        notificationList.add(0, notification);
    }

    public void markAllAsRead() {
        for (Notification notification : notificationList) {
            notification.setRead(true);
        }
    }
}
