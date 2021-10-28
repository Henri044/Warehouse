package ggc.core;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.Reader;

import ggc.core.exception.BadEntryException;

import ggc.core.product.Product;
import ggc.core.product.SimpleProduct;
import ggc.core.product.AggregateProduct;

import java.util.ArrayList;



public class Parser {

    private Warehouse _store;

    public Parser(Warehouse w) {
        _store = w;
    }

    void parseFile(String filename) throws IOException, BadEntryException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
        String line;

        while ((line = reader.readLine()) != null)
            parseLine(line);
        }
    }

    private void parseLine(String line) throws BadEntryException, BadEntryException {
        String[] components = line.split("\\|");

        switch (components[0]) {
            case "PARTNER":
                parsePartner(components, line);
                break;
        
            case "BATCH_S":
                parseSimpleProduct(components, line);
                break;

            case "BATCH_M":
                parseAggregateProduct(components, line);
                break;
        
            default:
                throw new BadEntryException("Invalid type element: " + components[0]);
        }
    }

    //PARTNER|id|nome|endereço
    private void parsePartner(String[] components, String line) throws BadEntryException {
        if (components.length != 4)
            throw new BadEntryException("Invalid partner with wrong number of fields (4): " + line);
    
        String id = components[1];
        String name = components[2];
        String address = components[3];
  
        try {
            _store.registerPartner(id, name, address);
        } catch (Exception e) {
        // DO NOTHING
        }
    }

    //BATCH_S|idProduto|idParceiro|preco|stock-actual
    private void parseSimpleProduct(String[] components, String line) throws BadEntryException {
        if (components.length != 5)
            throw new BadEntryException("Invalid number of fields (4) in simple batch description: " + line);
    
        String idProduct = components[1];
        String idPartner = components[2];
        double price = Double.parseDouble(components[3]);
        int stock = Integer.parseInt(components[4]);
    
        if (!_store.hasProduct(idProduct)) {
            _store.registerSimpleProduct(idProduct, price);
        }
    
        Product product = _store.getProduct(idProduct);
        Partner partner = _store.getPartner(idPartner);

        _store.registerBatch(price, stock, partner, idProduct);
    }
 
    
    //BATCH_M|idProduto|idParceiro|preco|stock-actual|agravamento|componente-1:quantidade-1#...#componente-n:quantidade-n
    private void parseAggregateProduct(String[] components, String line) throws BadEntryException {
        if (components.length != 7)
            throw new BadEntryException("Invalid number of fields (7) in aggregate batch description: " + line);
    
        String idProduct = components[1];
        String idPartner = components[2];
        double price = Double.parseDouble(components[3]);
        int stock = Integer.parseInt(components[4]);
        double alpha = Double.parseDouble(components[5]);

        if (!_store.hasProduct(idProduct)) {
            ArrayList<Product> products = new ArrayList<>();
            ArrayList<Integer> quantities = new ArrayList<>();

            for (String component : components[6].split("#")) {
                String[] recipeComponent = component.split(":");
  
                products.add(_store.getProduct(recipeComponent[0]));
                quantities.add(Integer.parseInt(recipeComponent[1]));
            }

            Recipe recipe = new Recipe(alpha, products, quantities);

            if (!_store.hasProduct(idProduct)) {
                _store.registerAggregateProduct(idProduct, price, recipe);
            }

            Product product = _store.getProduct(idProduct);
            Partner partner = _store.getPartner(idPartner);

            _store.registerBatch(price, stock, partner, idProduct);
        }
    }
}
