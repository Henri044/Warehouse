package ggc.core;

import ggc.core.Partner;
import ggc.core.product.Product;

import java.io.Serializable;

public class Batch implements Serializable {
    private double _priceUnit;
    private int _stock;
    private Partner _provider;
    private Product _product;

    public Batch(double price, int stock, Partner provider, Product product) {
        _priceUnit = price;
        _stock = stock;
        _provider = provider;
        _product = product;
    }

    public Product getProduct() {
        return _product;
    }

    public double getPrice() {
        return _priceUnit;
    }

    public int getStock() {
        return _stock;
    }

    public Partner getProvider() {
        return _provider;
    } 

    public String toString() {
        return (_product.getId() + "|" + _provider.getId() + "|" + Math.round(_priceUnit) + "|" + _stock);
    }

    public void deleteBatch() {
        if (_stock == 0) {}
            return;
    }

}
