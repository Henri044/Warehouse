package ggc.core;

import ggc.core.product.Product;
import ggc.core.Notification;

public class NewNotification extends Notification {

	public NewNotification (Product product, double price) {
		super(product, price);
	}

	public String toString() {
		return "NEW|" + this.getProduct().getId() + "|" + Math.round(this.getPrice());
	}
}