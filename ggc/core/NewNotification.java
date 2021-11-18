package ggc.core;

import ggc.core.product.Product;
import ggc.core.Notification;

import java.io.Serializable;

public class NewNotification extends Notification implements Serializable {

	public NewNotification (Product product, double price) {
		super(product, price);
	}

	public String toString() {
		return "NEW|" + this.getProduct().getId() + "|" + Math.round(this.getPrice());
	}
}