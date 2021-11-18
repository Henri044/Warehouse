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
import ggc.core.exception.NonAvailableAggregateProductStockException;
import ggc.core.exception.NonExistentTransactionKeyException;
import ggc.core.product.Product;
import ggc.core.Partner;
import ggc.core.Date;
import ggc.core.Parser;
import ggc.core.product.SimpleProduct;
import ggc.core.product.AggregateProduct;
import ggc.core.transaction.Transaction;
import ggc.core.transaction.sale.SaleByCredit;
import ggc.core.transaction.sale.BreakdownSale;
import ggc.core.transaction.sale.Sale;
import ggc.core.transaction.Acquisition;
import ggc.core.Recipe;
import ggc.core.transaction.Transaction;
import ggc.core.Notification;
import ggc.core.BargainNotification;
import ggc.core.NewNotification;
import ggc.core.comparators.BatchComparator;
import ggc.core.comparators.PartnersComparator;
import ggc.core.comparators.ProductsComparator;

public class Warehouse implements Serializable {

    // ATRIBUTES

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202109192006L;
    private double _balance;
    private double _accountingBalance;
    private Date _date;
    private HashMap<String, Partner> _partners;
    private HashMap<String, Product> _products;
    private ArrayList<Transaction> _transactions;

    // CONSTRUCTOR

    Warehouse() {

        _date = new Date();
        _partners = new HashMap<>();
        _products = new HashMap<String, Product>();
        _transactions = new ArrayList<Transaction>();
    }

    // ************************* DATE ***********************************

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

    // ************************* BALANCE ***********************************

    public double getBalance() {
        return _balance;
    }

    public double getAccountingBalance() {
        return _accountingBalance;
    }

    // ************************* PARTNER ***********************************

    public void registerPartner(String id, String name, String address) throws SamePartnerKeyException {

        Partner newPartner = new Partner(id, name, address);

        // Checks if the partner to register is already registered
        if (_partners.containsKey(id.toLowerCase()))
            throw new SamePartnerKeyException();

        // Puts the new partner in the list of partners
        _partners.put(id.toLowerCase(), newPartner);

        for (Product p : _products.values()) {
            p.subscribe(newPartner);
        }
    }

    public String partnerToString(String id) throws NonExistentPartnerKeyException {

        String toPrint = new String();

        // Checks if the partner is in the list of partners, if not throws the exception
        if (!_partners.containsKey(id.toLowerCase()))
            throw new NonExistentPartnerKeyException();

        // Searchs for the partner in the list of partners
        Partner partner = _partners.get(id.toLowerCase());

        toPrint = partner.toString() + "\n";

        for (Notification n : partner.getNotifications()) {
            toPrint += n.toString() + "\n";
        }

        partner.clearNotifs();

        toPrint = toPrint.substring(0, toPrint.length() - 1);

        return toPrint;
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

    public boolean hasPartner(String id) {
        return _partners.containsKey(id.toLowerCase());
    }

    public Partner getPartner(String id) {
        return _partners.get(id.toLowerCase());
    }

    // ************************* PRODUCT ***********************************

    public boolean hasProduct(String id) {
        return _products.containsKey(id.toLowerCase());
    }

    public Product getProduct(String id) {
        return _products.get(id.toLowerCase());
    }

    public void registerSimpleProduct(String idProduct, double price) {

        SimpleProduct newProduct = new SimpleProduct(idProduct, price);

        for (Partner p : _partners.values()) {
            newProduct.subscribe(p);
        }

        // Puts the new product in the list of products
        _products.put(idProduct.toLowerCase(), newProduct);
    }

    public void registerAggregateProduct(String idProduct, double price, Recipe recipe) {

        AggregateProduct newProduct = new AggregateProduct(idProduct, price, recipe);

        // Put every single existent Partner interested in the product notification
        for (Partner p : _partners.values()) {
            newProduct.subscribe(p);
        }

        // Puts the new product in the list of products
        _products.put(idProduct.toLowerCase(), newProduct);
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

    // ************************* BATCHES ***********************************

    public void registerBatch(double price, int stock, Partner partner, String idProduct) {

        Batch newBatch = new Batch(price, stock, partner, this.getProduct(idProduct));

        this.getProduct(idProduct).addBatch(newBatch);

        // Adds the stock to the product itself
        Product currentProduct = this.getProduct(idProduct);
        currentProduct.addStock(stock);

        // Checks if the price set in the new batch is higher than the current highest value
        // If so, change the max price
        if (price > currentProduct.getPrice()) {
            this.getProduct(idProduct).setMaxPrice(price);
        }
        if (price < currentProduct.getMinPrice()) {
            this.getProduct(idProduct).setMinPrice(price);
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

    // ************************* TRANSACTIONS ***********************************
  
    public void registerNewSale(String idPartner, int deadline, String idProductToSell, int quantity) 
    throws NonExistentPartnerKeyException, NonExistentProductKeyException, NonAvailableProductStockException, NonAvailableAggregateProductStockException{
        int i = 0;

        if (!this.hasPartner(idPartner))
            throw new NonExistentPartnerKeyException();
        if (!this.hasProduct(idProductToSell))
            throw new NonExistentProductKeyException();
        // Checks if there is available stock in the warehouse
        if (this.getProduct(idProductToSell).isSimpleProduct() && (quantity > this.getProduct(idProductToSell).getTotalStock()))
            throw new NonAvailableProductStockException();

        if (!this.getProduct(idProductToSell).isSimpleProduct() && (quantity > this.getProduct(idProductToSell).getTotalStock())) {
            int neededStock = quantity - this.getProduct(idProductToSell).getTotalStock();

            ArrayList<Product> components = this.getProduct(idProductToSell).getRecipe().getComponents();
            ArrayList<Integer> quantities = this.getProduct(idProductToSell).getRecipe().getQuantities();

            while (components.size() > i) {
                if((neededStock*quantities.get(i)) > components.get(i).getTotalStock()) {
                    throw new NonAvailableAggregateProductStockException(components.get(i).getId(), neededStock*quantities.get(i), components.get(i).getTotalStock());
                }
                i++;
            }
        }

        if ((quantity > this.getProduct(idProductToSell).getTotalStock()))
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
            //BALANCE??? WITH INTERESTS??
            sale = new SaleByCredit(idTransaction, baseValue, quantity, this.getPartner(idPartner), this.getProduct(idProductToSell), deadline);
            //_balance += baseValue;
        }

        //Add the sale to the transactions list
        _transactions.add(sale);
    }

    public void registerBreakdownSale(String idPartner, String idProduct, int quantity) 
    throws NonAvailableProductStockException, NonExistentPartnerKeyException, NonExistentProductKeyException {
        int idTransaction = _transactions.size();
        int i = 0;
        double price = 0;
        double priceAllComponents = 0;

        if (!_partners.containsKey(idPartner.toLowerCase()))
            throw new NonExistentPartnerKeyException();

        if (!_products.containsKey(idProduct.toLowerCase()))
            throw new NonExistentProductKeyException();

        if (this.getProduct(idProduct).getTotalStock() < quantity)
            throw new NonAvailableProductStockException();

        double priceProduct = this.getProduct(idProduct).getPrice()*quantity;
        BreakdownSale newBreakdownSale = new BreakdownSale(idTransaction, this.getProduct(idProduct).getPrice(), quantity, this.getPartner(idPartner), this.getProduct(idProduct), this.getDays());
        Recipe productRecipe = this.getProduct(idProduct).getRecipe();

        if(!this.getProduct(idProduct).isSimpleProduct()) {

            this.getProduct(idProduct).removeStock(quantity);
            Batch cheapestBatch = this.getProduct(idProduct).getCheapestBatch();
            cheapestBatch.removeStock(quantity);

            if (cheapestBatch.getStock() == 0) {
                // Deletes the batch
                this.getProduct(idProduct).deleteBatchesWithNoStock();
            }

            ArrayList<Product> components = productRecipe.getComponents();
            ArrayList<Integer> quantities = productRecipe.getQuantities();
            while (components.size() > i) {

                Product product = components.get(i);
                quantity = quantities.get(i);
                if (product.getBatches().size() != 0) {
                    price += product.getCheapestBatch().getPrice();
                }
                else {
                    price += product.getPrice();
                }

                priceAllComponents += price*quantity;
                registerBatch(price, quantity, this.getPartner(idPartner), product.getId());
                i++;
            }

            if ((priceProduct - priceAllComponents) > 0) {
                _balance += priceProduct - priceAllComponents;
                _accountingBalance += priceProduct - priceAllComponents;
            }
            _transactions.add(newBreakdownSale);
        }
    }

    public void registerAcquisition(double price, int stock, String idPartner, String idProduct) throws NonExistentPartnerKeyException {
        int idTransaction = _transactions.size();
        double baseValue = price * stock;
        Acquisition newAcquisition = new Acquisition(idTransaction, this.getDays(), baseValue, stock, this.getPartner(idPartner), this.getProduct(idProduct));

        if (!_partners.containsKey(idPartner.toLowerCase()))
            throw new NonExistentPartnerKeyException();

        if (this.getProduct(idProduct).getTotalStock() == 0) {
            this.registerNewProductNotification(idProduct, idPartner, price);
        }

        registerBatch(price, stock, this.getPartner(idPartner), idProduct);

        _transactions.add(newAcquisition);
        this.getPartner(idPartner).addAcquisitionValue(price * stock);
        _balance -= baseValue; 
        _accountingBalance -= baseValue;
    }

    public String transactionsPaidByPartnerToString(String idPartner) throws NonExistentPartnerKeyException {
        List<Transaction> transactionsPaidByPartner = new ArrayList<>(); 
        String toPrint = new String();

        if (!_partners.containsKey(idPartner.toLowerCase()))
            throw new NonExistentPartnerKeyException();

        for (Transaction t : _transactions) {
            if (t.isPaid() && t.getPartner().getId().toLowerCase().equals(idPartner.toLowerCase()))
                transactionsPaidByPartner.add(t);
        }

        for (Transaction tp : transactionsPaidByPartner) {
            toPrint += tp.toString() + "\n";
        }

        if (!toPrint.isEmpty()) {
            toPrint = toPrint.substring(0, toPrint.length() - 1);
        }

        return toPrint;
    }

    public String salesByPartnerToString(String idPartner) throws NonExistentPartnerKeyException {
        List<Transaction> salesByPartner = new ArrayList<>(); 
        String toPrint = new String();

        if (!_partners.containsKey(idPartner.toLowerCase()))
            throw new NonExistentPartnerKeyException();

        for (Transaction t : _transactions) {
            if (!t.isAcquisition() && t.getPartner().getId().toLowerCase().equals(idPartner.toLowerCase()))
                salesByPartner.add(t);
        }

        for (Transaction a : salesByPartner) {
            toPrint += a.toString() + "\n";
        }

        if (!toPrint.isEmpty()) {
            toPrint = toPrint.substring(0, toPrint.length() - 1);
        }

        return toPrint;
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

    public void receivePayment(int idTransaction) throws NonExistentTransactionKeyException {

        if (idTransaction >= _transactions.size() || idTransaction < 0)
            throw new NonExistentTransactionKeyException();

        Transaction newSale = _transactions.get(idTransaction);

        if (!newSale.isSaleByCredit())
            return;

        if (newSale.isPaid())
            return;

        ((SaleByCredit)newSale).toggleIsPaid();
    }    
        
    // ************************* RECIPE ***********************************

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

    // ************************* NOTIFICATIONS ***********************************

    public void toggleNotifs(String idPartner, String idProduct) throws NonExistentPartnerKeyException, NonExistentProductKeyException {

        if (!_partners.containsKey(idPartner.toLowerCase()))
            throw new NonExistentPartnerKeyException();
        if (!_products.containsKey(idProduct.toLowerCase()))
            throw new NonExistentProductKeyException();

        Partner partner = this.getPartner(idPartner);
        Product product = this.getProduct(idProduct);

        product.toggle(partner);
    }

    public void registerNewProductNotification(String idProduct, String idPartner, double price) {
        NewNotification notif = new NewNotification(this.getProduct(idProduct), price);
        this.getProduct(idProduct).notifyObserver(notif);
        this.getPartner(idPartner).getNotifications().remove(notif);
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
