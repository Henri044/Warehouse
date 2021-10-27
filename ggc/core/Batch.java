package ggc.core;

import ggc.core.Partner;

import java.io.Serializable;

public class Batch implements Serializable {
    private double _priceUnit;
    private int _stock;
    private Partner _provider;

    public Batch(double price, int stock, Partner provider){
        _priceUnit = price;
        _stock = stock;
        _provider = provider;
    }

    public double getPrice(){
        return _priceUnit;
    }

    public int getStock(){
        return _stock;
    }

    public String toString(){
        return ""; //TO DO
    }

    public void deleteBatch() {
        if (_stock == 0) {}
            return;
    }

}
