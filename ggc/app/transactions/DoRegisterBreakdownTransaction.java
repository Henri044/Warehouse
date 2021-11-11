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

  private int _stock;;
  private String _idProduct;
  private String _idPartner;

  public DoRegisterBreakdownTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_BREAKDOWN_TRANSACTION, receiver);
    addStringField("idPartner", Message.requestPartnerKey());
    addStringField("idProduct", Message.requestProductKey());
    addIntegerField("stock", Message.requestAmount());
  }

  @Override
  public final void execute() throws CommandException, UnavailableProductException, UnknownProductKeyException, UnknownPartnerKeyException {

    _idPartner = stringField("idPartner");
    _idProduct = stringField("idProduct");
    _stock = integerField("stock");

    try {

      _receiver.registerBreakdownSale(_idPartner,_idProduct, _stock);
    } catch (NonExistentPartnerKeyException nepke) {
      throw new UnknownPartnerKeyException(_idPartner);
    } catch (NonExistentProductKeyException neprke) {
      throw new UnknownProductKeyException(_idProduct);
    } catch (NonAvailableProductStockException napse) {
      throw new UnavailableProductException(_idProduct, _stock, _receiver.getAvailableStockFromProduct(_idProduct));
    }
  }

}