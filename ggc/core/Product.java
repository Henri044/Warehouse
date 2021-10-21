package ggc.core;
import java.util.List;

public abstract class Product {
    private double _maxPrice;
    private String _id;
    private List<Batch> _batches;

    public Product(){

    }

    public List<Product> breakdown(){

    }

    public String getBatchesBelowLimit(int value){

    }

    public String toString(){

    }

    public abstract checkQuantity(int quantity, Partner p){
        
    }
}
