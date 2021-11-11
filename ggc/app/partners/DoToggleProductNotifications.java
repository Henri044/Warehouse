package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;

import ggc.app.partners.Message;

import ggc.app.exception.UnknownPartnerKeyException;
import ggc.app.exception.UnknownProductKeyException;

import ggc.core.exception.NonExistentPartnerKeyException;
import ggc.core.exception.NonExistentProductKeyException;

/**
 * Toggle product-related notifications.
 */
class DoToggleProductNotifications extends Command<WarehouseManager> {

  private String _idPartner;
  private String _idProduct;

  DoToggleProductNotifications(WarehouseManager receiver) {
    super(Label.TOGGLE_PRODUCT_NOTIFICATIONS, receiver);

    addStringField("idPartner", Message.requestPartnerKey());
    addStringField("idProduct", Message.requestProductKey());
  }

  @Override
  public void execute() throws CommandException, UnknownPartnerKeyException, UnknownProductKeyException {
    _idPartner = stringField("idPartner");
    _idProduct = stringField("idProduct");

    try {
      _receiver.toggleNotifs(_idPartner, _idProduct);
    } catch (NonExistentPartnerKeyException nepke) {
      throw new UnknownPartnerKeyException(_idPartner);
    } catch (NonExistentProductKeyException neprke) {
      throw new UnknownProductKeyException(_idProduct);
    }
  }

}
