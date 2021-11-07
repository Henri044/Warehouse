package ggc.app.products;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;

import ggc.app.products.Message;

import ggc.app.exception.UnknownProductKeyException;

import ggc.core.exception.NonExistentProductKeyException;


/**
 * Show all products.
 */
class DoShowBatchesByProduct extends Command<WarehouseManager> {

  private String _key;

  DoShowBatchesByProduct(WarehouseManager receiver) {
    super(Label.SHOW_BATCHES_BY_PRODUCT, receiver);
    addStringField("key", Message.requestProductKey());
  }

  @Override
  public final void execute() throws CommandException, UnknownProductKeyException {
    _key = stringField("key");

    try {
      String batches = _receiver.batchesByProductToString(_key);
      _display.addLine(batches);
      _display.display();

    } catch (NonExistentProductKeyException neprke) {
      throw new UnknownProductKeyException(_key);
    }
  }

}
