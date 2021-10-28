package ggc.core.product;

import ggc.core.product.Product;
import ggc.core.Partner;

import java.io.Serializable;

public class SimpleProduct extends Product implements Serializable {

    // CONSTRUCTOR
    
    public SimpleProduct(String productId, double maxPrice) {
        super(productId, maxPrice);
    }

    @Override
    public void checkQuantity(int quantity, Partner provider){

    }

    /**
    * Abstract Method.
    * Returns what should be displayed on the App.
    *
    * @return a String with what should be displayed.
    */

    @Override
    public String toString() {
        return (getId() + "|" + Math.round(getPrice()) + "|" + getTotalStock());
    }

}
