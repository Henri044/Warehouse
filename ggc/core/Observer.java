package ggc.core;

import ggc.core.Notification;

public interface Observer {
	// Create constructor?

	public abstract void update(Notification notif);
}