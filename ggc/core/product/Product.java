package ggc.core.product;

import java.util.List;
import java.util.ArrayList;

import ggc.core.Batch;
import ggc.core.Partner;

import java.io.Serializable;


public abstract class Product implements Serializable {
    private double _maxPrice;
    private String _id;
    private List<Batch> _batches;

    Product(){
        //TO DO
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
}
