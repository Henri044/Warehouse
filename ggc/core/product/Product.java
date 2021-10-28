package ggc.core.product;

import java.util.ArrayList;

import ggc.core.Batch;
import ggc.core.Partner;

import java.io.Serializable;


public abstract class Product implements Serializable {

    // ATRIBUTES

    private String _id;
    private double _maxPrice;
    private int _totalStock;
    private ArrayList<Batch> _batches;

    // CONSTRUCTOR

    Product(String productId, double maxPrice){
        _id = productId;
        _maxPrice = maxPrice;
        _batches = new ArrayList<Batch>();
    }

    /**
    * Gets the ID of the Product.
    *
    * @return the ID of the Product.
    */

    public String getId() {
        return _id;
    }

    /**
    * Gets the Price of the Product.
    *
    * @return the price of the Product.
    */

    public double getPrice() {
        return _maxPrice;
    }

    /**
    * Gets the Total Stock of the Product.
    *
    * @return the Total Stock of the Product.
    */

    public int getTotalStock() {
        return _totalStock;
    }

    /**
    * Gets an ArrayList of all the Batches of 
    * a certain Product.
    *
    * @return an ArrayList of the Batches of
    * the Product.
    */

    public ArrayList<Batch> getBatches() {
        return _batches;
    }

    /**
    * Adds some Stock to the Product.
    *
    * @param quantity This is the quantity to add.
    * @return void.
    */

    public void addStock(int quantity) {
        _totalStock += quantity;
    }

    /**
    * Sets the ArrayList of Batches of a
    * certain Product.
    *
    * @param batches This is the ArrayList of Batches.
    * @return void.
    */

    public void setBatches(ArrayList<Batch> batches) {
        _batches = batches;
    }

    /**
    * Abstract Method.
    * Returns what should be displayed on the App.
    *
    * @return a String with what should be displayed.
    */

    public abstract String toString();

    public abstract void checkQuantity(int quantity, Partner provider); // TO DO
}
