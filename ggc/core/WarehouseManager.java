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

import ggc.core.exception.BadEntryException;
import ggc.core.exception.ImportFileException;
import ggc.core.exception.UnavailableFileException;
import ggc.core.exception.MissingFileAssociationException;
import ggc.core.exception.NonPositiveDateException;
import ggc.core.exception.SamePartnerKeyException;
import ggc.core.exception.NonExistentPartnerKeyException;
import ggc.core.exception.NonExistentProductKeyException;
import ggc.core.exception.NonAvailableProductStockException;


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
        try {
            _warehouse.advanceDays(days);
        } catch (NonPositiveDateException ide) { throw ide; }
    }

    public double getBalance() {
        return _warehouse.getBalance();
    }

    public double getAccountingBalance() {
        return _warehouse.getAccountingBalance();
    }

    public void registerPartner(String id, String name, String address) throws SamePartnerKeyException {
        try {
            _warehouse.registerPartner(id, name, address);
        } catch (SamePartnerKeyException spke) { throw spke; }
    }

    public String partnerToString(String id) throws NonExistentPartnerKeyException{
        try {
            return _warehouse.partnerToString(id);
        } catch (NonExistentPartnerKeyException nepk) { throw nepk; }
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
        try {
            return _warehouse.batchesByProductToString(idProduct);
        } catch (NonExistentProductKeyException neprke) { throw neprke; }
    }

    public String batchesByPartnerToString(String id) throws NonExistentPartnerKeyException {
        try {
            return _warehouse.batchesByPartnerToString(id); 
        } catch (NonExistentPartnerKeyException nepk) { throw nepk; }
    }

    public String batchesBelowLimitToString(int limit) {
        return _warehouse.batchesBelowLimitToString(limit);
    }

    public int getAvailableStockFromProduct(String idProduct) {
        return _warehouse.getProduct(idProduct).getTotalStock();
    }

    public void registerSale(String idPartner, Date deadline, String idProduct, int quantity) throws NonExistentPartnerKeyException, NonExistentProductKeyException, NonAvailableProductStockException {
        // VERIFICAR AQUI SE É POR CREDITO OU DIRETO???
        try {
            _warehouse.registerSale(idPartner, deadline, idProduct, quantity);
        } catch (NonExistentPartnerKeyException nepk) { throw nepk; }
        catch (NonExistentProductKeyException neprk) { throw neprk; }
        catch (NonAvailableProductStockException napse) { throw napse; }
    }
}
