package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;

import ggc.app.transactions.Message; 

import ggc.core.exception.NonExistentPartnerKeyException;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.core.exception.NonExistentProductKeyException;
import ggc.app.exception.UnknownProductKeyException;
import ggc.core.exception.NonAvailableProductStockException;
import ggc.app.exception.UnavailableProductException;

public class DoRegisterBreakdownTransaction extends Command<WarehouseManager> {

  private int stock;;
  private String idProduct;
  private String idPartner;

  public DoRegisterBreakdownTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_BREAKDOWN_TRANSACTION, receiver);
    addStringField("idPartner", Message.requestPartnerKey());
    addStringField("idProduct", Message.requestProductKey());
    addIntegerField("quantity", Message.requestAmount());
  }

  @Override
  public final void execute() throws CommandException, UnavailableProductException, UnknownProductKeyException, UnknownPartnerKeyException {

    idPartner = stringField("idPartner");
    idProduct = stringField("idProduct");
    stock = integerField("quantity");

    try {

      _receiver.registerBreakdownSale(idPartner, idProduct, stock);
    } catch (NonExistentPartnerKeyException nepke) {
      throw new UnknownPartnerKeyException(idPartner);
    } catch (NonExistentProductKeyException neprke) {
      throw new UnknownProductKeyException(idProduct);
    } catch (NonAvailableProductStockException napse) {
      throw new UnavailableProductException(idProduct, stock, _receiver.getAvailableStockFromProduct(idProduct));
    }
  }

}