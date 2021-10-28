package ggc.core;

import java.util.ArrayList;

import ggc.core.product.Product;

public class Recipe {

    private double _alpha;
    private ArrayList<Product> _products;
    private ArrayList<Integer> _quantities;

    public Recipe(double alpha, ArrayList<Product> products, ArrayList<Integer> quantities) {
        _alpha = alpha;
        _products = new ArrayList<Product>(products);
        _quantities = new ArrayList<Integer>(quantities);
    }

    public String toString(){
        String recipe = new String();

        for (int i = 0; i < _products.size(); i++) {
            recipe += (_products.get(i).getId() + ":" + _quantities.get(i) + "#");
        }

        recipe = recipe.substring(0, recipe.length() - 1);

        return recipe;
    }
}
