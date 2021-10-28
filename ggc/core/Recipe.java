package ggc.core;

import java.util.ArrayList;

import ggc.core.product.Product;

public class Recipe {

    private double _alpha;
    private ArrayList<Product> _products = new ArrayList<Product>();
    private ArrayList<Integer> _quantities = new ArrayList<Integer>();

    public Recipe(double alpha, ArrayList<Product> products, ArrayList<Integer> quantities) {
        _alpha = alpha;
        _products = (ArrayList<Product>)products.clone();
        _quantities = (ArrayList<Integer>)quantities.clone();
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
