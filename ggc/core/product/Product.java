package ggc.core.product;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ggc.core.Batch;
import ggc.core.Partner;
import ggc.core.Observer;
import ggc.core.Recipe;
import ggc.core.Notification;
import ggc.core.BargainNotification;

import java.io.Serializable;


public abstract class Product implements Serializable {

    // ATRIBUTES

    private String _id;
    private double _maxPrice;
    private double _minPrice;
    private int _totalStock;
    private ArrayList<Batch> _batches;
    private List<Observer> _observers = new ArrayList<Observer>();

    // CONSTRUCTOR

    Product(String productId, double maxPrice){
        _id = productId;
        _maxPrice = maxPrice;
        _minPrice = maxPrice;
        _batches = new ArrayList<Batch>();
    }

    /**
    * Gets the ID of the Product.
    *
    * @return the ID of the Product.
    */

    public String getId() {
        return _id;
    }

    /**
    * Gets the Price of the Product.
    *
    * @return the price of the Product.
    */

    public double getPrice() {
        return _maxPrice;
    }

    public double getMinPrice() {
        return _minPrice;
    }

    /**
    * Gets the Total Stock of the Product.
    *
    * @return the Total Stock of the Product.
    */

    public int getTotalStock() {
        return _totalStock;
    }

    /**
    * Gets an ArrayList of all the Batches of 
    * a certain Product.
    *
    * @return an ArrayList of the Batches of
    * the Product.
    */

    public ArrayList<Batch> getBatches() {
        return _batches;
    }

    public Batch getCheapestBatch() {
        Batch cheapestBatch = _batches.get(0);

        for (Batch b : _batches) {
            if (b.getPrice() < cheapestBatch.getPrice()) {
                cheapestBatch = b;
            }
        }

        return cheapestBatch;
    }

    public List<Observer> getObservers() {
        return _observers;
    }

    public void removeStock(int n) {
        _totalStock -= n;
    }

    /**
    * Adds some Stock to the Product.
    *
    * @param quantity This is the quantity to add.
    * @return void.
    */

    public void addStock(int quantity) {
        _totalStock += quantity;
    }

    /**
    * Sets the ArrayList of Batches of a
    * certain Product.
    *
    * @param batches This is the ArrayList of Batches.
    * @return void.
    */

    public void setBatches(ArrayList<Batch> batches) {
        _batches = batches;
    }

    public void addBatch(Batch b) {
        _batches.add(b);
        if (_minPrice > b.getPrice()) {
            BargainNotification notif = new BargainNotification(this, b.getPrice());
            notifyObserver(notif);
        }
    }

    public void setMaxPrice(double maxPrice) {
        _maxPrice = maxPrice;
    }

    public void setMinPrice(double minPrice) {
        _minPrice = minPrice;
    }

    /**
    * Abstract Method.
    * Returns what should be displayed on the App.
    *
    * @return a String with what should be displayed.
    */

    public void deleteBatchesWithNoStock() {
        Iterator<Batch> iter = _batches.iterator();

        while (iter.hasNext()) {
            Batch b = iter.next();
            if (b.getStock() == 0)
                iter.remove();
        }
    }

    public void subscribe(Observer o) {
        _observers.add(o);
    }

    public void unsubscribe(Observer o) {
        Iterator<Observer> iter = _observers.iterator();

        while (iter.hasNext()) {
            Observer obs = iter.next();
            if (obs == o) {
                iter.remove();
            }
        }
    }

    public void toggle(Observer partner) {
        for (Observer obs : _observers) {
            if (obs == partner) {
                unsubscribe(obs);
                return;
            }
        }
        subscribe(partner);
    }
    
    public void notifyObserver(Notification n) {
        for (Observer obs : _observers) {
            obs.update(n);
        }
    }

    public abstract Recipe getRecipe();    
    
    public abstract boolean isSimpleProduct();

    public abstract String toString();

    public abstract void checkQuantity(int quantity, Partner provider); // TO DO
}
