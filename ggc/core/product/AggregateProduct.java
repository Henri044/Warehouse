package ggc.core.product;

import ggc.core.product.Product;
import ggc.core.Partner;

public class AggregateProduct extends Product{
    
    AggregateProduct(String id){

    }

    @Override
    void checkQuantity(int quantity, Partner provider){
        return; //TO DO
    }

    public String toString(){
        return ""; //TO DO
    }
}
