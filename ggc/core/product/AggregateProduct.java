package ggc.core.product;

import ggc.core.product.Product;
import ggc.core.Partner;
import ggc.core.Recipe;

import java.io.Serializable;

public class AggregateProduct extends Product implements Serializable {

    Recipe _recipe;
    
    public AggregateProduct(String productId, double maxPrice, Recipe recipe){
        super(productId, maxPrice);
        _recipe = recipe;
    }

    @Override
    public void checkQuantity(int quantity, Partner provider){
        return; //TO DO
    }

    @Override
    public String toString(){
        return (getId() + "|" + Math.round(getPrice()) + "|" + getTotalStock() + "|" + _recipe.toString());
    }
}
