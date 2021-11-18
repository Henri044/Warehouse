package ggc.core;

import ggc.core.product.Product;

import java.io.Serializable;

public abstract class Notification implements Serializable {
    private Product _product;
    private double _price;

    public Notification(Product product, double price) {
        _product = product;
        _price = price;
    }

    public Product getProduct() {
        return _product;
    }

    public double getPrice() {
        return _price;
    }

    public abstract String toString();
}
