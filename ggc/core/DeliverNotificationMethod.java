package ggc.core;

import ggc.core.Notification;

import java.util.ArrayList;

public interface DeliverNotificationMethod {
	void addNotification(Notification notif, ArrayList<Notification> notifications);
}