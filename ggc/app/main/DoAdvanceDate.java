package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import ggc.core.WarehouseManager;

import ggc.app.main.Message;
import ggc.app.exception.InvalidDateException;

import ggc.core.exception.NonPositiveDateException;

//FIXME import classes

/**
 * Advance current date.
 */
class DoAdvanceDate extends Command<WarehouseManager> {

  private int _daysToAdvance;
  private int _newDate;

  DoAdvanceDate(WarehouseManager receiver) {
    super(Label.ADVANCE_DATE, receiver);
    //FIXME add command fields
    addIntegerField("daysToAdvance", Message.requestDaysToAdvance());
  }

  @Override
  public final void execute() throws CommandException, InvalidDateException {
    //FIXME implement command
    _daysToAdvance = integerField("daysToAdvance");

    try {
      _receiver.requestDaysToAdvance(_daysToAdvance);
    } catch (NonPositiveDateException ide) {
      throw new InvalidDateException(_daysToAdvance);
    }
    

    //_newDate = _receiver.currentDate();

  }

}
