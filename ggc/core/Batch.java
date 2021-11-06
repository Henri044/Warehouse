package ggc.core;

import ggc.core.Partner;
import ggc.core.product.Product;

import java.io.Serializable;

public class Batch implements Serializable {

    // ATRIBUTES

    private double _priceUnit;
    private int _stock;
    private Partner _provider;
    private Product _product;

    // CONSTRUCTOR

    public Batch(double price, int stock, Partner provider, Product product) {
        _priceUnit = price;
        _stock = stock;
        _provider = provider;
        _product = product;
    }

    /**
    * Gets the Price of the Batch.
    *
    * @return the Price of the Batch.
    */

    public Double getPrice() {
        return _priceUnit;
    }

    /**
    * Gets the Stock of a Batch.
    *
    * @return the Stock of the Batch.
    */

    public Integer getStock() {
        return _stock;
    }

    /**
    * Gets the Product releated to the Batch.
    *
    * @return the Product related to the Batch.
    */

    public Product getProduct() {
        return _product;
    }

    /**
    * Gets the Partner related to the Batch.
    *
    * @return the Partner related to the Batch.
    */

    public Partner getProvider() {
        return _provider;
    }

    /**
    * Returns what should be displayed on the App.
    *
    * @return a String with what should be displayed.
    */

    public String toString() {
        return (_product.getId() + "|" + _provider.getId() + "|" + Math.round(_priceUnit) + "|" + _stock);
    }
}
