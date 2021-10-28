package ggc.core;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collection;
import java.util.Collections;

import java.io.Serializable;
import java.io.IOException;

import ggc.core.exception.BadEntryException;
import ggc.core.exception.NonPositiveDateException;
import ggc.core.exception.SamePartnerKeyException;
import ggc.core.exception.NonExistentPartnerKeyException;
import ggc.core.product.Product;
import ggc.core.Partner;
import ggc.core.Date;
import ggc.core.Parser;
import ggc.core.product.SimpleProduct;
import ggc.core.product.AggregateProduct;

public class Warehouse implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202109192006L;
    private Date _date;
    private int _nextTransictionId;
    private HashMap<String, Partner> _partners;
    private float _balance;
    //private float _contabilisticBalance;
    //private HashMap<String, SimpleProduct> _simpleProducts;
    //private HashMap<String, AggregateProduct> _aggregateProducts;
    private HashMap<String, ArrayList<Batch>> _batches;
    private HashMap<String, Product> _products;

    Warehouse() {

        _date = new Date();
        _nextTransictionId = 0;
        _partners = new HashMap<>();
        //_simpleProducts = new HashMap<>();
        //_aggregateProducts = new HashMap<>();
        _products = new HashMap<String, Product>();
        _batches = new HashMap<String, ArrayList<Batch>>();
    }

    public int getDate() {
        return _date.getDays();
    }

    public void advanceDays(int days) throws NonPositiveDateException {
        try {
            _date.add(days);
        } catch (NonPositiveDateException ide) { throw ide; }
    }

    public int getBalance() {
        return (int)_balance;
    }

    public void registerPartner(String id, String name, String address) throws SamePartnerKeyException{
        Partner newPartner = new Partner(id, name, address);

        if (_partners.containsKey(id.toLowerCase()))
            throw new SamePartnerKeyException();

        _partners.put(id.toLowerCase(), newPartner);
    }

    public String partnerToString(String id) throws NonExistentPartnerKeyException {
        if (!_partners.containsKey(id.toLowerCase()))
            throw new NonExistentPartnerKeyException();

        Partner partner = _partners.get(id.toLowerCase());

        return partner.toString();
    }

    public Partner getPartner(String id) {
        return _partners.get(id.toLowerCase());
    }

    public boolean hasProduct(String id) {
        return _products.containsKey(id);
    }

    public Product getProduct(String id) {
        return _products.get(id);
    }

    public void registerSimpleProduct(String idProduct, double price) {
        SimpleProduct newProduct = new SimpleProduct(idProduct, price);
        _products.put(idProduct, newProduct);
    }

    public void registerAggregateProduct(String idProduct, double price, Recipe recipe) {
        AggregateProduct newProduct = new AggregateProduct(idProduct, price, recipe);
        _products.put(idProduct, newProduct);
    }

    public void registerBatch(double price, int stock, Partner partner, String idProduct) {
        Batch newBatch = new Batch(price, stock, partner, _products.get(idProduct));

        if (_batches.containsKey(idProduct)) {
            ArrayList<Batch> productBatches = _batches.get(idProduct);
            productBatches.add(newBatch);
            _batches.put(idProduct, productBatches);
            _products.get(idProduct).setBatches(productBatches);
        }
        else {
            ArrayList<Batch> newProductBatches = new ArrayList<Batch>();
            newProductBatches.add(newBatch);
            _batches.put(idProduct, newProductBatches);
            _products.get(idProduct).setBatches(newProductBatches);
        }
        Product currentProduct = _products.get(idProduct);
        currentProduct.addStock(stock);
    }

    public class ProductsComparator implements Comparator<Product> {

        public int compare(Product p1, Product p2){
            return p1.getId().compareTo(p2.getId());
        }
    }

    public String allProductsToString() {
        Collection<Product> values = _products.values();
        List<Product> unsortedProducts = new ArrayList<Product>(values);
        List<Product> sortedProducts = new ArrayList<>(unsortedProducts);
        String toPrint = new String();

        Collections.sort(sortedProducts, new ProductsComparator());

        for (Product p : sortedProducts) {
            toPrint += p.toString() + "\n";
        }

        if (!toPrint.isEmpty()) {
            toPrint = toPrint.substring(0, toPrint.length() - 1);
        }

        return toPrint;
    }

    public class PartnersComparator implements Comparator<Partner> {

        public int compare(Partner p1, Partner p2){
            return p1.getId().compareTo(p2.getId());
        }
    }

    public String allPartnersToString() {
        Collection<Partner> values = _partners.values();
        List<Partner> unsortedPartners = new ArrayList<Partner>(values);
        List<Partner> sortedPartners = new ArrayList<>(unsortedPartners);
        String toPrint = new String();

        Collections.sort(sortedPartners, new PartnersComparator());

        for (int i = 0; i < sortedPartners.size(); i++) {
            toPrint += sortedPartners.get(i).toString() + "\n";
        } 

        if (!toPrint.isEmpty()) {
            toPrint = toPrint.substring(0, toPrint.length() - 1);
        }
  
        return toPrint;
    }

    public class BatchComparator implements Comparator<Batch> {

        public int compare(Batch b1, Batch b2) {
            int compareProductId = b1.getProduct().getId().compareTo(b2.getProduct().getId());
            int comparePartnerId = b1.getProvider().getId().compareTo(b2.getProvider().getId());
            int comparePrice = String.valueOf(b1.getPrice()).compareTo(String.valueOf(b2.getPrice()));
            int compareQuantity = String.valueOf(b1.getStock()).compareTo(String.valueOf(b2.getStock()));

            if (compareProductId != 0) {
                return compareProductId;
            }
            else if (comparePartnerId != 0) {
                return comparePartnerId;
            }
            else if (comparePrice != 0) {
                return comparePrice;
            }
            else {
                return compareQuantity;
            }
        }
    }

    public String allBatchesToString() {
        ArrayList<Batch> batches = new ArrayList<>();
        String toPrint = new String();

        for (Product p : _products.values()) {
            batches.addAll(p.getBatches());
        }

        Collections.sort(batches, new BatchComparator());

        for (Batch b : batches) {
            toPrint += b.toString() + "\n";
        }


        if (!toPrint.isEmpty()) {
            toPrint = toPrint.substring(0, toPrint.length() - 1);
        }
    
        return toPrint;
    }

    void importFile(String txtfile) throws IOException, BadEntryException {
        Parser parser = new Parser(this);

        try {
            parser.parseFile(txtfile);
        } catch (IOException | BadEntryException e) {
            throw e;
        }
    }

}
