package ggc.core;

import java.io.Serializable;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.ObjectOutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

import java.util.ArrayList;

import ggc.core.exception.BadEntryException;
import ggc.core.exception.ImportFileException;
import ggc.core.exception.UnavailableFileException;
import ggc.core.exception.MissingFileAssociationException;
import ggc.core.exception.NonPositiveDateException;
import ggc.core.exception.SamePartnerKeyException;
import ggc.core.exception.NonExistentPartnerKeyException;
import ggc.core.exception.NonExistentProductKeyException;
import ggc.core.exception.NonAvailableProductStockException;
import ggc.core.exception.NonExistentTransactionKeyException;
import ggc.core.exception.NonAvailableAggregateProductStockException;

public class WarehouseManager {

    private String _filename = "";
    private String _loadFile = new String();

    private Warehouse _warehouse = new Warehouse();

    public WarehouseManager() {
    }

    public void open() throws ClassNotFoundException, IOException {
        try {
        ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(_filename)));
        _warehouse = (Warehouse) ois.readObject();
        ois.close();
    }
        catch (ClassNotFoundException cnfe) { throw cnfe; }
        catch (IOException e) { throw e; }

        _loadFile = _filename;
    }

    public void save() throws IOException, FileNotFoundException, MissingFileAssociationException {
        if (!hasLoadFile())
            setLoadFile(_filename);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(
                new BufferedOutputStream(
                    new FileOutputStream(getLoadFile())));
            oos.writeObject(_warehouse);
            oos.close();
        }
        catch (FileNotFoundException fnfe) { throw fnfe; }
        catch (IOException e) { throw e; }
        }

    public void saveAs(String filename) throws MissingFileAssociationException, FileNotFoundException, IOException {
        _filename = filename;
        save();
    }

    public void load(String filename) throws UnavailableFileException, ClassNotFoundException {
        try {
            _filename = filename;
            open();
        }
        catch (ClassNotFoundException cnfe) {
            throw cnfe;
        }
        catch (IOException e) {
            throw new UnavailableFileException(_filename);
        }
    }

    public void importFile(String textfile) throws ImportFileException {
        try {
            _warehouse.importFile(textfile);
        } catch (IOException | BadEntryException e) {
            throw new ImportFileException(textfile, e);
        }
    }

    public void setLoadFile(String loadFile) {
        _loadFile = loadFile;
    }

    public String getLoadFile() {
        return _loadFile;
    }

    public boolean hasLoadFile() {
        return !_loadFile.isEmpty();
    }

    public int currentDays() {
        return _warehouse.getDays();
    }

    public Date currentDate() {
        return _warehouse.getDate();
    }

    public void requestDaysToAdvance(int days) throws NonPositiveDateException {
        _warehouse.advanceDays(days);
    }

    public double getBalance() {
        return _warehouse.getBalance();
    }

    public double getAccountingBalance() {
        return _warehouse.getAccountingBalance();
    }

    public void registerPartner(String id, String name, String address) throws SamePartnerKeyException {
        _warehouse.registerPartner(id, name, address);
    }

    public String partnerToString(String id) throws NonExistentPartnerKeyException{
        return _warehouse.partnerToString(id);
    }

    public String allPartnersToString() {
        return _warehouse.allPartnersToString();
    }

    public String allProductsToString() {
        return _warehouse.allProductsToString();
    }

    public String allBatchesToString() {
        return _warehouse.allBatchesToString();
    }

    public String batchesByProductToString(String idProduct) throws NonExistentProductKeyException {
        return _warehouse.batchesByProductToString(idProduct);
    }

    public String batchesByPartnerToString(String id) throws NonExistentPartnerKeyException {
        return _warehouse.batchesByPartnerToString(id); 
    }

    public String batchesBelowLimitToString(int limit) {
        return _warehouse.batchesBelowLimitToString(limit);
    }

    public boolean hasProduct(String idProduct) {
        return _warehouse.hasProduct(idProduct);
    }

    public int getAvailableStockFromProduct(String idProduct) {
        return _warehouse.getProduct(idProduct).getTotalStock();
    }

    public void registerSimpleProduct(String idProduct, double price) {
        _warehouse.registerSimpleProduct(idProduct, price);
    }

    public void registerAggregateProduct(String idProduct, double price, Recipe recipe) {
        _warehouse.registerAggregateProduct(idProduct, price, recipe);
    }

    public void registerNewSale(String idPartner, int deadline, String idProduct, int quantity) 
    throws NonExistentPartnerKeyException, NonExistentProductKeyException, NonAvailableProductStockException, NonAvailableAggregateProductStockException {
        _warehouse.registerNewSale(idPartner, deadline, idProduct, quantity);
    }

    public void registerAcquisition(double price, int stock, String idPartner, String idProduct) throws NonExistentPartnerKeyException {
        _warehouse.registerAcquisition(price, stock, idPartner, idProduct);
    }

    public Recipe registerRecipe(double alpha, ArrayList<String> idComponents, ArrayList<Integer> quantities) throws NonExistentProductKeyException {
        return _warehouse.registerRecipe(alpha, idComponents, quantities);
    }

    public String acquisitionsByPartnerToString(String idPartner) throws NonExistentPartnerKeyException {
        return _warehouse.acquisitionsByPartnerToString(idPartner);
    }

    public String transactionToString(int idTransaction) throws NonExistentTransactionKeyException {
        return _warehouse.transactionToString(idTransaction);
    }

    public void toggleNotifs(String idPartner, String idProduct) throws NonExistentPartnerKeyException, NonExistentProductKeyException {
        _warehouse.toggleNotifs(idPartner, idProduct);
    }

    public String transactionsPaidByPartnerToString(String idPartner) throws NonExistentPartnerKeyException {
        return _warehouse.transactionsPaidByPartnerToString(idPartner);
    }

    public String salesByPartnerToString(String idPartner) throws NonExistentPartnerKeyException {
        return _warehouse.salesByPartnerToString(idPartner);
    }

    public void registerNewProductNotification(String idProduct, String idPartner, double price) {
        _warehouse.registerNewProductNotification(idProduct, idPartner, price);
    }

    public void registerBreakdownSale(String idPartner, String idProduct, int quantity) 
    throws NonAvailableProductStockException, NonExistentPartnerKeyException, NonExistentProductKeyException {
        _warehouse.registerBreakdownSale(idPartner, idProduct, quantity);
    }

    public void receivePayment(int idTransaction) throws NonExistentTransactionKeyException {
        _warehouse.receivePayment(idTransaction);
    }
}
