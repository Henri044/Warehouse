package ggc.core.product;

import java.util.List;
import java.util.ArrayList;

import ggc.core.Batch;
import ggc.core.Partner;

import java.io.Serializable;


public abstract class Product implements Serializable {
    
    private String _id;
    private double _maxPrice;
    private List<Batch> _batches;
    private int _totalStock;

    Product(String productId, double maxPrice){
        _id = productId;
        _maxPrice = maxPrice;
        _batches = new ArrayList<Batch>();
    }

    public List<Batch> breakdown(){
        _batches = new ArrayList<>();
        return _batches; //TO DO
    }

    public String getBatchesBelowLimit(int value){
        return ""; //TO DO
    }

    public String toString(){
        return ""; //TO DO
    }

    public abstract void checkQuantity(int quantity, Partner provider);

    public void addStock(int quantity) {
        _totalStock += quantity;
    }

}
