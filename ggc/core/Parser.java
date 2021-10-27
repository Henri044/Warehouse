package ggc.core;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.Reader;

import ggc.core.exception.BadEntryException;

import ggc.core.product.SimpleProduct;
import ggc.core.product.AggregateProduct;



public class Parser {

  // It could be WarehouseManager too. Or something else.
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
    
    // add code here to
    // register partner with id, name, address in _store;
    try {
      _store.registerPartner(id, name, address); //ASK PROF SE ASSUMIMOS QUE JÁ VEM BEM FEITO O INPUT
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
    
    // add code here to do the following
    //if (!_store does not have product with idProduct)
    if (!_store.hasSimpleProduct(idProduct)) {
      //  register simple product with idProduct in _store;
      _store.registerSimpleProduct(idProduct, price);
    }
    
    // add code here 
    //Product product = get Product in _store with productId;
    SimpleProduct product = _store.getSimpleProduct(idProduct);
    //Partner partner = get Partner in _store with partnerId;
    Partner partner = _store.getPartner(idPartner);

    // add code here to
    // add batch with price, stock and partner to product
    _store.registerBatch(price, stock, partner, idProduct);
  }
 
    
  //BATCH_M|idProduto|idParceiro|preco|stock-actual|agravamento|componente-1:quantidade-1#...#componente-n:quantidade-n
  private void parseAggregateProduct(String[] components, String line) throws BadEntryException {
    if (components.length != 7)
      throw new BadEntryException("Invalid number of fields (7) in aggregate batch description: " + line);
    
    String idProduct = components[1];
    String idPartner = components[2];
    /*
    double price = Double.parseDouble(components[3]);
    int stock = Integer.parseInt(components[4]);
    double aggravation = double.parseDouble(components[5]);
    */

    // add code here to do the following
    /*
    if (!_store does not have product with idProduct) {
      ArrayList<Product> products = new ArrayList<>();
      ArrayList<Integer> quantities = new ArrayList<>();
      
      for (String component : components[6].split("#")) {
        String[] recipeComponent = component.split(":");
        // add code here to 
        // products.add(get Product with id recipeComponent[0]);
        quantities.add(Integer.parseInt(recipeComponent[1]));
      }
      
      // add code here to 
      // register in _store aggregate product with idProduct, aggravation=Double.parseDouble(components[5])
      // and recipe given by products and quantities);
    }
    
    // add code here to 
    //Product product = get Product in _store with productId;
    //Partner partner = get Partner in _store with partnerId;
    double price = Double.parseDouble(components[3]);
    int stock = Integer.parseInt(components[4]);
    // add code here to
    // add batch with price, stock and partner to product
    */

    /*
    if (!_store.hasAggregateProduct(idProduct)) {
      //adicionar o produto à lista de produtos
      // VER DAS QUANTIDADES!!!
    }
    for (String  : components[6].split("#")) {
      String[] recipeComponent = component.split(":");
      //adicionar produtos à receita
    }
    // registar na warehouse o produto agregado
    _store.registerAggregateProduct(idProduct, price); // falta o agravamento / receita;
    */
    
  }
}
