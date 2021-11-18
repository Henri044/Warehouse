package ggc.core;

import ggc.core.product.Product;
import ggc.core.Notification;

import java.io.Serializable;

public class BargainNotification extends Notification implements Serializable {

	public BargainNotification(Product product, double price) {
		super(product, price);
	}

	public String toString() {
		return "BARGAIN|" + this.getProduct().getId() + "|" + Math.round(this.getPrice());
	}
}