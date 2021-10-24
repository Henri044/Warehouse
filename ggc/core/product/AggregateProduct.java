package ggc.core.product;

import ggc.core.product.Product;
import ggc.core.Partner;

import java.io.Serializable;

public class AggregateProduct extends Product implements Serializable {
    
    public AggregateProduct(String id){
        super(); //TO DO
    }

    @Override
    public void checkQuantity(int quantity, Partner provider){
        return; //TO DO
    }

    public String toString(){
        return ""; //TO DO
    }
}
