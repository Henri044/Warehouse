package ggc.core.product;

import ggc.core.product.Product;
import ggc.core.Partner;

import java.io.Serializable;

public class SimpleProduct extends Product implements Serializable {
    
    public SimpleProduct(String id) {
        super(); //TO DO
    }

    public String toString(){
        return ""; //TO DO
    }

    @Override
    public void checkQuantity(int quantity, Partner provider){

    }
}
