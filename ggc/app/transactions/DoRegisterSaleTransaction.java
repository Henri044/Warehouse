package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.core.Date;

import ggc.app.transactions.Message;

import ggc.core.exception.NonAvailableProductStockException;
import ggc.core.exception.NonPositiveDateException;
import ggc.core.exception.NonExistentPartnerKeyException;
import ggc.core.exception.NonExistentProductKeyException;

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
  private Date _deadline;
  private int _quantity; 

  public DoRegisterSaleTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_SALE_TRANSACTION, receiver);
    addStringField("idPartner", Message.requestPartnerKey());
    addIntegerField("deadline", Message.requestPaymentDeadline());
    addStringField("idProduct", Message.requestProductKey());
    addIntegerField("quantity", Message.requestAmount());
  }

  @Override
  public final void execute() throws CommandException, UnknownPartnerKeyException, InvalidDateException, UnknownProductKeyException, UnavailableProductException {
    _idPartner = stringField("idPartner");
    _deadline = new Date();
    _idProduct = stringField("idProduct");
    _quantity = integerField("quantity");

    int deadlineDays = integerField("deadline");

    try {
      _deadline.add(_receiver.currentDays() + deadlineDays);
      //REGISTER NEW SALE
      _receiver.registerSale(_idPartner, _deadline, _idProduct, _quantity);
    } catch (NonExistentPartnerKeyException nepke) {
      throw new UnknownPartnerKeyException(_idPartner);
    } catch (NonPositiveDateException npde) {
      throw new InvalidDateException(deadlineDays);
    } catch (NonExistentProductKeyException neprke) {
      throw new UnknownProductKeyException(_idProduct);
    } catch (NonAvailableProductStockException napse) {
      throw new UnavailableProductException(_idProduct, _quantity, _receiver.getAvailableStockFromProduct(_idProduct));
    }
  }

}
