package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
//FIXME import classes

import ggc.app.partners.Message;

import ggc.app.exception.UnknownPartnerKeyException;

import ggc.core.exception.NonExistentPartnerKeyException;

/**
 * Show partner.
 */
class DoShowPartner extends Command<WarehouseManager> {

  private String _key;

  DoShowPartner(WarehouseManager receiver) {
    super(Label.SHOW_PARTNER, receiver);
    //FIXME add command fields
    addStringField("key", Message.requestPartnerKey());

  }

  @Override
  public void execute() throws CommandException, UnknownPartnerKeyException {
    //FIXME implement command
    _key = stringField("key");

    try {
      String partner = _receiver.showPartner(_key);
      _display.addLine(partner);
      _display.display();

    } catch (NonExistentPartnerKeyException nepke) {
      throw new UnknownPartnerKeyException(_key);
    }
  }

}
