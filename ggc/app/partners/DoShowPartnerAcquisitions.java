package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;

import ggc.app.partners.Message;

import ggc.app.exception.UnknownPartnerKeyException;

import ggc.core.exception.NonExistentPartnerKeyException;

class DoShowPartnerAcquisitions extends Command<WarehouseManager> {

  private String _idPartner;

  DoShowPartnerAcquisitions(WarehouseManager receiver) {
    super(Label.SHOW_PARTNER_ACQUISITIONS, receiver);
    addStringField("idPartner", Message.requestPartnerKey()); 
  }

  @Override
  public void execute() throws CommandException, UnknownPartnerKeyException {
    _idPartner = stringField("idPartner");

    try {

      String acquisitions = _receiver.acquisitionsByPartnerToString(_idPartner);
      _display.addLine(acquisitions);
      _display.display();

    } catch (NonExistentPartnerKeyException nepke) {
      throw new UnknownPartnerKeyException(_idPartner);
    }
  }

}
