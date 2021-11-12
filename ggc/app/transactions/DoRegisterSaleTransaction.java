package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;

import ggc.app.transactions.Message;

import ggc.core.exception.NonAvailableProductStockException;
import ggc.core.exception.NonExistentPartnerKeyException;
import ggc.core.exception.NonExistentProductKeyException;
import ggc.core.exception.NonAvailableAggregateProductStockException;

import ggc.app.exception.UnavailableProductException;
import ggc.app.exception.InvalidDateException;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.app.exception.UnknownProductKeyException;

/**
 * 
 */
public class DoRegisterSaleTransaction extends Command<WarehouseManager> {

  private String _idPartner;
  private String _idProduct;
  private int _deadline;
  private int _quantity; 

  public DoRegisterSaleTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_SALE_TRANSACTION, receiver);
    addStringField("idPartner", Message.requestPartnerKey());
    addIntegerField("deadline", Message.requestPaymentDeadline());
    addStringField("idProduct", Message.requestProductKey());
    addIntegerField("quantity", Message.requestAmount());
  }
  
  @Override
  public final void execute() throws CommandException, UnknownPartnerKeyException, UnknownProductKeyException, UnavailableProductException {
    _idPartner = stringField("idPartner");
    _deadline = integerField("deadline");
    _idProduct = stringField("idProduct");
    _quantity = integerField("quantity");

    try {
      //REGISTER NEW SALE
      _receiver.registerNewSale(_idPartner, _deadline, _idProduct, _quantity);
    } catch (NonExistentPartnerKeyException nepke) {
      throw new UnknownPartnerKeyException(_idPartner);
    } catch (NonExistentProductKeyException neprke) {
      throw new UnknownProductKeyException(_idProduct);
    } catch (NonAvailableProductStockException napse) {
      throw new UnavailableProductException(_idProduct, _quantity, _receiver.getAvailableStockFromProduct(_idProduct));
    } catch (NonAvailableAggregateProductStockException naapse) {
      throw new UnavailableProductException(naapse.getIdProduct(), naapse.getRequestAmount(), naapse.getAvailableAmount());
    }
  }

}
