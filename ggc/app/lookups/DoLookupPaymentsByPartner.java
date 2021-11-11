package ggc.app.lookups;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;

import ggc.app.lookups.Message;

import ggc.app.exception.UnknownPartnerKeyException;

import ggc.core.exception.NonExistentPartnerKeyException;

public class DoLookupPaymentsByPartner extends Command<WarehouseManager> {

  private String _idPartner;

  public DoLookupPaymentsByPartner(WarehouseManager receiver) {
    super(Label.PAID_BY_PARTNER, receiver);
    addStringField("idPartner", Message.requestPartnerKey());
  }

  @Override
  public void execute() throws CommandException, UnknownPartnerKeyException {
    _idPartner = stringField("idPartner");

    try {

      String transactionsPaid = _receiver.transactionsPaidByPartnerToString(_idPartner);
      _display.addLine(transactionsPaid);
      _display.display();

    } catch (NonExistentPartnerKeyException nepke) {
      throw new UnknownPartnerKeyException(_idPartner);
    }
  }

}
