package ggc.core;

public class Batch {
    private double _priceUnit;
    private int _quantity;
    private Partner _provider;

    public Batch(){

    }

    public double getPrice(){
        return _priceUnit;
    }

    public int getQuantity(){
        return _quantity;
    }

    public String toString(){

    }

    public void deleteBatch(){
        if (_quantity == 0)
            
    }
}
