package ggc.app.lookups;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;

import ggc.app.lookups.Message;

/**
 * Lookup products cheaper than a given price.
 */
public class DoLookupProductBatchesUnderGivenPrice extends Command<WarehouseManager> {

  private int _limit;

  public DoLookupProductBatchesUnderGivenPrice(WarehouseManager receiver) {
    super(Label.PRODUCTS_UNDER_PRICE, receiver);
    addIntegerField("limit", Message.requestPriceLimit());
  }

  @Override
  public void execute() throws CommandException {
    _limit = integerField("limit");

    String batches = _receiver.batchesBelowLimitToString(_limit);
    _display.addLine(batches);
    _display.display();
  }

}
