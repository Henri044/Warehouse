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
import ggc.core.exception.NonAvailableProductStockException;
import ggc.core.exception.NonExistentTransactionKeyException;
import ggc.core.product.Product;
import ggc.core.Partner;
import ggc.core.Date;
import ggc.core.Parser;
import ggc.core.product.SimpleProduct;
import ggc.core.product.AggregateProduct;
import ggc.core.transaction.Transaction;
import ggc.core.transaction.sale.SaleByCredit;
import ggc.core.transaction.sale.Sale;
import ggc.core.transaction.Acquisition;
import ggc.core.Recipe;
import ggc.core.transaction.Transaction;

public class Warehouse implements Serializable {

    // ATRIBUTES

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202109192006L;
    //private int _nextTransactionId;
    private double _balance;
    private double _accountingBalance;
    private Date _date;
    private HashMap<String, Partner> _partners;
    private HashMap<String, Product> _products;
    private ArrayList<Transaction> _transactions;

    // CONSTRUCTOR

    Warehouse() {

        _date = new Date();
        //_nextTransactionId = 0;
        _partners = new HashMap<>();
        _products = new HashMap<String, Product>();
        _transactions = new ArrayList<Transaction>();
    }

    public int getDays() {
        return _date.getDays();
    }

    public Date getDate() {
        return _date.now();
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

    public void registerPartner(String id, String name, String address) throws SamePartnerKeyException {

        Partner newPartner = new Partner(id, name, address);

        // Checks if the partner to register is already registered
        if (_partners.containsKey(id.toLowerCase()))
            throw new SamePartnerKeyException();

        // Puts the new partner in the list of partners
        _partners.put(id.toLowerCase(), newPartner);
    }

    public String partnerToString(String id) throws NonExistentPartnerKeyException {

        // Checks if the partner is in the list of partners, if not throws the exception
        if (!_partners.containsKey(id.toLowerCase()))
            throw new NonExistentPartnerKeyException();

        // Searchs for the partner in the list of partners
        Partner partner = _partners.get(id.toLowerCase());

        return partner.toString();
    }

    public boolean hasPartner(String id) {
        return _partners.containsKey(id.toLowerCase());
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

        // Puts the new product in the list of products
        _products.put(idProduct.toLowerCase(), newProduct);
    }

    public void registerAggregateProduct(String idProduct, double price, Recipe recipe) {

        AggregateProduct newProduct = new AggregateProduct(idProduct, price, recipe);

        // Puts the new product in the list of products
        _products.put(idProduct.toLowerCase(), newProduct);
    }

    public void registerBatch(double price, int stock, Partner partner, String idProduct) {

        Batch newBatch = new Batch(price, stock, partner, this.getProduct(idProduct));

        // Checks if the product already has any batch
        // If so add the new batch
        if ((this.getProduct(idProduct).getBatches()).size() != 0) {
            ArrayList<Batch> productBatches = this.getProduct(idProduct).getBatches();
            productBatches.add(newBatch);
            this.getProduct(idProduct).setBatches(productBatches);
        }

        // Otherwise create a new array of batches for the new product
        else {
            ArrayList<Batch> newProductBatches = new ArrayList<Batch>();
            newProductBatches.add(newBatch);
            this.getProduct(idProduct).setBatches(newProductBatches);
        }

        // Adds the stock to the product itself
        Product currentProduct = this.getProduct(idProduct);
        currentProduct.addStock(stock);

        // Checks if the price set in the new batch is higher than the current highest value
        // If so, change the max price
        if (price > this.getProduct(idProduct).getPrice()) {
            this.getProduct(idProduct).setMaxPrice(price);
        }
    }

    public String batchesByPartnerToString(String idPartner) throws NonExistentPartnerKeyException {
        ArrayList<Batch> batchesByPartner = new ArrayList<>();
        ArrayList<Batch> batches = new ArrayList<>();
        String toPrint = new String();

        // Checks if the partner is already registered
        if (!_partners.containsKey(idPartner.toLowerCase()))
            throw new NonExistentPartnerKeyException();

        // Gets all the batches available in the warehouse
        for (Product p : _products.values()) {
            batches.addAll(p.getBatches());
        }

        // Filter the batches provided by the partner
        for (Batch b: batches) {
            if ((b.getProvider()).getId().toLowerCase().equals(idPartner.toLowerCase()))
                batchesByPartner.add(b);
        }

        // Sorts the filtered batches
        Collections.sort(batchesByPartner, new BatchComparator());

        // Gets all the batches to String
        for (Batch b: batchesByPartner) {
            toPrint += b.toString() + "\n";
        }

        if (!toPrint.isEmpty()) {
            toPrint = toPrint.substring(0, toPrint.length() - 1);
        }

        return toPrint;
    }

    public String batchesByProductToString(String idProduct) throws NonExistentProductKeyException {

        // Checks if the product is registered
        if (!_products.containsKey(idProduct.toLowerCase()))
            throw new NonExistentProductKeyException();

        String toPrint = new String();

        // Gets all the batches by the specific product
        ArrayList<Batch> batches = this.getProduct(idProduct).getBatches();

        // Sorts the batches
        Collections.sort(batches, new BatchComparator());

        // Gets all the batches to String
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

        // Gets all the batches available in the warehouse
        for (Product p : _products.values()) {
            batches.addAll(p.getBatches());
        }

        // Filters the batches that have a lower value than the limit
        for (Batch b : batches) {
            if (b.getPrice() < limit)
                batchesBelowLimit.add(b);
        }

        // Sorts the batches 
        Collections.sort(batchesBelowLimit, new BatchComparator());

        // Gets all the batches to String
        for (Batch b : batchesBelowLimit) {
            toPrint += b.toString() + "\n";
        }

        if (!toPrint.isEmpty()) {
            toPrint = toPrint.substring(0, toPrint.length() - 1);
        }

        return toPrint;
    }

    public class ProductsComparator implements Comparator<Product> {

        // Compare and sort the products by their ID's
        public int compare(Product p1, Product p2){
            return p1.getId().toLowerCase().compareTo(p2.getId().toLowerCase());
        }
    }

    public String allProductsToString() {
        Collection<Product> values = _products.values();
        List<Product> unsortedProducts = new ArrayList<Product>(values);
        List<Product> sortedProducts = new ArrayList<>(unsortedProducts);
        String toPrint = new String();

        // Sorts the products
        Collections.sort(sortedProducts, new ProductsComparator());

        // Gets all the products to String
        for (Product p : sortedProducts) {
            toPrint += p.toString() + "\n";
        }

        if (!toPrint.isEmpty()) {
            toPrint = toPrint.substring(0, toPrint.length() - 1);
        }

        return toPrint;
    }

    public class PartnersComparator implements Comparator<Partner> {

        // Compare and sort the partners by their ID's
        public int compare(Partner p1, Partner p2){
            return p1.getId().toLowerCase().compareTo(p2.getId().toLowerCase());
        }
    }

    public String allPartnersToString() {
        Collection<Partner> values = _partners.values();
        List<Partner> unsortedPartners = new ArrayList<Partner>(values);
        List<Partner> sortedPartners = new ArrayList<>(unsortedPartners);
        String toPrint = new String();

        // Sorts the partners
        Collections.sort(sortedPartners, new PartnersComparator());

        // Gets all the partners to String
        for (int i = 0; i < sortedPartners.size(); i++) {
            toPrint += sortedPartners.get(i).toString() + "\n";
        } 

        if (!toPrint.isEmpty()) {
            toPrint = toPrint.substring(0, toPrint.length() - 1);
        }
  
        return toPrint;
    }

    public class BatchComparator implements Comparator<Batch> {

        // Compare and sort the batches by their product ID's, after partner ID's,
        // after price and finally quantity
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

        // Gets all the batches available in the warehouse
        for (Product p : _products.values()) {
            batches.addAll(p.getBatches());
        }

        // Sorts the batches
        Collections.sort(batches, new BatchComparator());

        // Gets all the batches to String
        for (Batch b : batches) {
            toPrint += b.toString() + "\n";
        }

        if (!toPrint.isEmpty()) {
            toPrint = toPrint.substring(0, toPrint.length() - 1);
        }
    
        return toPrint;
    }
  
    public void registerNewSale(String idPartner, int deadline, String idProductToSell, int quantity) throws NonExistentPartnerKeyException, NonExistentProductKeyException, NonAvailableProductStockException {
        if (!this.hasPartner(idPartner))
            throw new NonExistentPartnerKeyException();
        if (!this.hasProduct(idProductToSell))
            throw new NonExistentProductKeyException();
        // Checks if there is available stock in the warehouse
        if (quantity > this.getProduct(idProductToSell).getTotalStock())
            throw new NonAvailableProductStockException();

        // Register new transaction
        registerSaleByCredit(quantity, idPartner, idProductToSell, deadline);
    }

    public void registerSaleByCredit(int quantity, String idPartner, String idProductToSell, int deadline) {

        int idTransaction = _transactions.size();
        // Calculate Base Value
        int remainingQuantity = quantity;
        double baseValue = 0;
        SaleByCredit sale;

        while (remainingQuantity != 0) {
            Batch cheapestBatch = this.getProduct(idProductToSell).getCheapestBatch();
            baseValue += this.getProduct(idProductToSell).getCheapestBatch().getPrice();
            cheapestBatch.removeStock(1);
            this.getProduct(idProductToSell).removeStock(1);
            remainingQuantity -= 1;

            if (cheapestBatch.getStock() == 0) {
                // Deletes the batch
                this.getProduct(idProductToSell).deleteBatchesWithNoStock();
            }
        }

        _accountingBalance += baseValue;

        // If deadline is the current date, the sale is normal, otherwise the sale is by credit
        if (deadline == _date.getDays()) {
            //REGISTER SALE BY CREDIT WITHOUT INTEREST
            //System.out.println("REGISTA VENDA NORMAL");
            _balance += baseValue;
            sale = new SaleByCredit(idTransaction, baseValue, quantity, _date.getDays(), this.getPartner(idPartner), this.getProduct(idProductToSell));
        }
        else {
            //REGISTER SALE BY CREDIT WITH INTEREST
            //System.out.println("REGISTA VENDA COM JUROS");
            //BALANCE??? WITH INTERESTS AND SHIT
            sale = new SaleByCredit(idTransaction, baseValue, quantity, this.getPartner(idPartner), this.getProduct(idProductToSell), deadline);
        }

        //Add the sale to the transactions list
        _transactions.add(sale);
    }

    public Recipe registerRecipe(double alpha, ArrayList<String> idComponents, ArrayList<Integer> quantities) throws NonExistentProductKeyException{
        ArrayList<Product> components = new ArrayList<Product>();

        for (String i : idComponents) {
            if (!_products.containsKey(i.toLowerCase()))
                throw new NonExistentProductKeyException();

            components.add(getProduct(i.toLowerCase()));
        }

        Recipe newRecipe = new Recipe(alpha, components, quantities);
        return newRecipe;
    }

    public void registerAcquisition(double price, int stock, String idPartner, String idProduct) throws NonExistentPartnerKeyException {
        int idTransaction = _transactions.size();
        Acquisition newAcquisition = new Acquisition(idTransaction, this.getDays(), price, stock, this.getPartner(idPartner), this.getProduct(idProduct));

        if (!_partners.containsKey(idPartner.toLowerCase()))
            throw new NonExistentPartnerKeyException();

        registerBatch(price, stock, this.getPartner(idPartner), idProduct);

        _transactions.add(newAcquisition);
        _balance -= _transactions.get(idTransaction).getPrice();
    }

    public String acquisitionsByPartnerToString(String idPartner) throws NonExistentPartnerKeyException {
        List<Transaction> acquisitionsByPartner = new ArrayList<>(); 
        String toPrint = new String();

        if (!_partners.containsKey(idPartner.toLowerCase()))
            throw new NonExistentPartnerKeyException();

        for (Transaction t : _transactions) {
            if (t.isAcquisition() && t.getPartner().getId().toLowerCase().equals(idPartner.toLowerCase()))
                acquisitionsByPartner.add(t);
        }

        for (Transaction a : acquisitionsByPartner) {
            toPrint += a.toString() + "\n";
        }

        if (!toPrint.isEmpty()) {
            toPrint = toPrint.substring(0, toPrint.length() - 1);
        }

        return toPrint;
    }

    public String transactionToString(int idTransaction) throws NonExistentTransactionKeyException {

        if (idTransaction >= _transactions.size())
            throw new NonExistentTransactionKeyException();

        return _transactions.get(idTransaction).toString();
    }

    void importFile(String txtfile) throws IOException, BadEntryException {
        Parser parser = new Parser(this);

        // If there is no valid text file, throws the exception
        try {
            parser.parseFile(txtfile);
        } catch (IOException | BadEntryException e) {
            throw e;
        }
    }

}
