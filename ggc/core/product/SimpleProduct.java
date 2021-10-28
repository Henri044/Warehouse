package ggc.core.product;

import ggc.core.product.Product;
import ggc.core.Partner;

import java.io.Serializable;

public class SimpleProduct extends Product implements Serializable {
    
    public SimpleProduct(String productId, double maxPrice) {
        super(productId, maxPrice);
    }

    @Override
    public void checkQuantity(int quantity, Partner provider){

    }

    @Override
    public String toString(){
        return (getId() + "|" + Math.round(getPrice()) + "|" + getTotalStock());
    }

}
