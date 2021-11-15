package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;

import ggc.app.transactions.Message;

import ggc.core.exception.NonExistentTransactionKeyException;

import ggc.app.exception.UnknownTransactionKeyException;

public class DoReceivePayment extends Command<WarehouseManager> {

  private int idTransaction;

  public DoReceivePayment(WarehouseManager receiver) {
    super(Label.RECEIVE_PAYMENT, receiver);
    addIntegerField("idTransaction", Message.requestTransactionKey());
  }

  @Override
  public final void execute() throws CommandException, UnknownTransactionKeyException {
    idTransaction = integerField("idTransaction");

    try {
      _receiver.receivePayment(idTransaction);
    } catch (NonExistentTransactionKeyException netke) {
        throw new UnknownTransactionKeyException(idTransaction);
    }
  }
}