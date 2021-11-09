package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;

import ggc.app.transactions.Message;

import ggc.app.exception.UnknownTransactionKeyException;
import ggc.core.exception.NonExistentTransactionKeyException;

public class DoShowTransaction extends Command<WarehouseManager> {

  private int _idTransaction;

  public DoShowTransaction(WarehouseManager receiver) {
    super(Label.SHOW_TRANSACTION, receiver);
    addIntegerField("idTransaction", Message.requestTransactionKey());

  }

  @Override
  public final void execute() throws CommandException, UnknownTransactionKeyException {
    _idTransaction = integerField("idTransaction");

    try {
      String transaction = _receiver.transactionToString(_idTransaction);
      _display.addLine(transaction);
      _display.display();

    } catch (NonExistentTransactionKeyException netke) {
      throw new UnknownTransactionKeyException(_idTransaction);
    }

  }

}