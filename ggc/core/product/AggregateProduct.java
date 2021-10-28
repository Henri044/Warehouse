package ggc.core.product;

import ggc.core.product.Product;
import ggc.core.Partner;
import ggc.core.Recipe;

import java.io.Serializable;

public class AggregateProduct extends Product implements Serializable {

    // ATRIBUTES

    Recipe _recipe;

    // CONSTRUCTOR
    
    public AggregateProduct(String productId, double maxPrice, Recipe recipe){
        super(productId, maxPrice);
        _recipe = recipe;
    }

    @Override
    public void checkQuantity(int quantity, Partner provider){
        return; //TO DO
    }

    /**
    * Returns what should be displayed on the App.
    *
    * @return a String with what should be displayed.
    */

    @Override
    public String toString() {
        return (getId() + "|" + Math.round(getPrice()) + "|" + getTotalStock() + "|" + _recipe.toString());
    }
}
