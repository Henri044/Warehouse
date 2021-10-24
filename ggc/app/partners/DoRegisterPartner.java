package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.core.exception.SamePartnerKeyException;

import ggc.app.partners.Message;

import ggc.app.exception.DuplicatePartnerKeyException;


//FIXME import classes

/**
 * Register new partner.
 */
class DoRegisterPartner extends Command<WarehouseManager> {

  private String _key;
  private String _name;
  private String _address;

  DoRegisterPartner(WarehouseManager receiver) {
    super(Label.REGISTER_PARTNER, receiver);
    //FIXME add command fields
    addStringField("key", Message.requestPartnerKey());
    addStringField("name", Message.requestPartnerName());
    addStringField("address", Message.requestPartnerAddress());
  }

  @Override
  public void execute() throws CommandException, DuplicatePartnerKeyException {
    //FIXME implement command
    _key = stringField("key");
    _name = stringField("name");
    _address = stringField("address");

    try {
      _receiver.registerPartner(_key, _name, _address);
    } catch (SamePartnerKeyException spke) {
      throw new DuplicatePartnerKeyException(_key);
    }

  }

}
