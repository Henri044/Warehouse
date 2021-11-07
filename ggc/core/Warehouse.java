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
import ggc.core.exception.NonExistentProductKeyException;
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
    private int _nextTransactionId;
    private HashMap<String, Partner> _partners;
    private double _balance;
    private double _accountingBalance;
    private HashMap<String, Product> _products;

    Warehouse() {

        _date = new Date();
        _nextTransactionId = 0;
        _partners = new HashMap<>();
        _products = new HashMap<String, Product>();
    }

    public int getDate() {
        return _date.getDays();
    }

    public void advanceDays(int days) throws NonPositiveDateException {
        try {
            _date.add(days);
        } catch (NonPositiveDateException ide) { throw ide; }
    }

    public double getBalance() {
        return _balance;
    }

    public double getAccountingBalance() {
        return _accountingBalance;
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
        return _products.containsKey(id.toLowerCase());
    }

    public Product getProduct(String id) {
        return _products.get(id.toLowerCase());
    }

    public void registerSimpleProduct(String idProduct, double price) {
        SimpleProduct newProduct = new SimpleProduct(idProduct, price);
        _products.put(idProduct.toLowerCase(), newProduct);
    }

    public void registerAggregateProduct(String idProduct, double price, Recipe recipe) {
        AggregateProduct newProduct = new AggregateProduct(idProduct, price, recipe);
        _products.put(idProduct.toLowerCase(), newProduct);
    }

    public void registerBatch(double price, int stock, Partner partner, String idProduct) {
        Batch newBatch = new Batch(price, stock, partner, _products.get(idProduct.toLowerCase()));

        if ((_products.get(idProduct.toLowerCase()).getBatches()).size() != 0) {
            ArrayList<Batch> productBatches = _products.get(idProduct.toLowerCase()).getBatches();
            productBatches.add(newBatch);
            _products.get(idProduct.toLowerCase()).setBatches(productBatches);
        }

        else {
            ArrayList<Batch> newProductBatches = new ArrayList<Batch>();
            newProductBatches.add(newBatch);
            _products.get(idProduct.toLowerCase()).setBatches(newProductBatches);
        }

        Product currentProduct = _products.get(idProduct.toLowerCase());
        currentProduct.addStock(stock);

        if (price > _products.get(idProduct.toLowerCase()).getPrice()) {
            _products.get(idProduct.toLowerCase()).setMaxPrice(price);
        }
    }

    public String batchesByPartnerToString(String idPartner) throws NonExistentPartnerKeyException {
        ArrayList<Batch> batchesByPartner = new ArrayList<>();
        ArrayList<Batch> batches = new ArrayList<>();
        String toPrint = new String();

        if (!_partners.containsKey(idPartner.toLowerCase()))
            throw new NonExistentPartnerKeyException();

        for (Product p : _products.values()) {
            batches.addAll(p.getBatches());
        }

        for (Batch b: batches) {
            if ((b.getProvider()).getId().toLowerCase().equals(idPartner.toLowerCase()))
                batchesByPartner.add(b);
        }

        Collections.sort(batchesByPartner, new BatchComparator());

        for (Batch b: batchesByPartner) {
            toPrint += b.toString() + "\n";
        }

        if (!toPrint.isEmpty()) {
            toPrint = toPrint.substring(0, toPrint.length() - 1);
        }

        return toPrint;
    }

    public String batchesByProductToString(String idProduct) throws NonExistentProductKeyException {
        if (!_products.containsKey(idProduct.toLowerCase()))
            throw new NonExistentProductKeyException();

        String toPrint = new String();

        ArrayList<Batch> batches = _products.get(idProduct.toLowerCase()).getBatches();

        Collections.sort(batches, new BatchComparator());

        for (Batch b : batches) {
            toPrint += b.toString() + "\n";
        }

        if (!toPrint.isEmpty()) {
            toPrint = toPrint.substring(0, toPrint.length() - 1);
        }

        return toPrint;
    }

    public String batchesBelowLimitToString(int limit) {
        ArrayList<Batch> batches = new ArrayList<>();
        ArrayList<Batch> batchesBelowLimit = new ArrayList<>();
        String toPrint = new String();

        for (Product p : _products.values()) {
            batches.addAll(p.getBatches());
        }

        for (Batch b : batches) {
            if (b.getPrice() < limit)
                batchesBelowLimit.add(b);
        }

        Collections.sort(batchesBelowLimit, new BatchComparator());

        for (Batch b : batchesBelowLimit) {
            toPrint += b.toString() + "\n";
        }

        if (!toPrint.isEmpty()) {
            toPrint = toPrint.substring(0, toPrint.length() - 1);
        }

        return toPrint;
    }

    public class ProductsComparator implements Comparator<Product> {

        public int compare(Product p1, Product p2){
            return p1.getId().toLowerCase().compareTo(p2.getId().toLowerCase());
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
            return p1.getId().toLowerCase().compareTo(p2.getId().toLowerCase());
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
            int compareProductId = b1.getProduct().getId().toLowerCase().compareTo(b2.getProduct().getId().toLowerCase());
            int comparePartnerId = b1.getProvider().getId().toLowerCase().compareTo(b2.getProvider().getId().toLowerCase());
            int comparePrice = b1.getPrice().compareTo(b2.getPrice());
            int compareQuantity = b1.getStock().compareTo(b2.getStock());

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
