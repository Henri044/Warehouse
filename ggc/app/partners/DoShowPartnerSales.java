package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;

import ggc.app.partners.Message;

import ggc.app.exception.UnknownPartnerKeyException;

import ggc.core.exception.NonExistentPartnerKeyException;

class DoShowPartnerSales extends Command<WarehouseManager> {

  private String _idPartner;

  DoShowPartnerSales(WarehouseManager receiver) {
    super(Label.SHOW_PARTNER_SALES, receiver);
    addStringField("idPartner", Message.requestPartnerKey());
  }

  @Override
  public void execute() throws CommandException, UnknownPartnerKeyException {
    _idPartner = stringField("idPartner");

    try {

      String sales = _receiver.salesByPartnerToString(_idPartner);
      _display.addLine(sales);
      _display.display();

    } catch (NonExistentPartnerKeyException nepke) {
      throw new UnknownPartnerKeyException(_idPartner);
    }
  }

}