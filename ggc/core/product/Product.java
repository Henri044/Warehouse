package ggc.core.product;

import java.util.ArrayList;

import ggc.core.Batch;
import ggc.core.Partner;

import java.io.Serializable;


public abstract class Product implements Serializable {
    
    private String _id;
    private double _maxPrice;
    private ArrayList<Batch> _batches;
    private int _totalStock;

    Product(String productId, double maxPrice){
        _id = productId;
        _maxPrice = maxPrice;
        _batches = new ArrayList<Batch>();
    }

    // public List<Batch> breakdown() {}

    public String getBatchesBelowLimit(int value) {
        return ""; //TO DO
    }

    public int getTotalStock() {
        return _totalStock;
    }

    public ArrayList<Batch> getBatches() {
        return _batches;
    }

    public String getId() {
        return _id;
    }

    public double getPrice() {
        return _maxPrice;
    }

    public void addStock(int quantity) {
        _totalStock += quantity;
    }

    public abstract String toString();

    public abstract void checkQuantity(int quantity, Partner provider);

}
