package ggc.core;

import java.io.Serializable;

import ggc.core.DeliverNotificationMethod;
import ggc.core.Notification;

import java.util.ArrayList;

public class DeliverNotification implements DeliverNotificationMethod, Serializable {
	public void addNotification(Notification notif, ArrayList<Notification> notifications) {
		notifications.add(notif);
	}
}