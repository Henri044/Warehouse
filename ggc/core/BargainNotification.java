package ggc.core;

import ggc.core.product.Product;
import ggc.core.Notification;

public class BargainNotification extends Notification {

	public BargainNotification(Product product, double price) {
		super(product, price);
	}

	public String toString() {
		return "BARGAIN|" + this.getProduct().getId() + "|" + Math.round(this.getPrice());
	}
}