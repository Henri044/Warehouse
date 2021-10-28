package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;

import ggc.core.WarehouseManager;
//FIXME import classes
import java.io.IOException;
import ggc.core.exception.MissingFileAssociationException;
import ggc.app.main.Message;
/**
 * Save current state to file under current name (if unnamed, query for name).
 */
class DoSaveFile extends Command<WarehouseManager> {

  private String _fileName;

  /** @param receiver */
  DoSaveFile(WarehouseManager receiver) {
    super(Label.SAVE, receiver);
    /*
    if (!_receiver.hasLoadFile()) {
      addStringField("fileName", Message.newSaveAs());
      System.out.println("a\n");
    }
    */
  }

  @Override
  public final void execute() throws CommandException {
    //FIXME implement command and create a local Form
    Form form = new Form();

    try {
      if (!_receiver.hasLoadFile()) {
        form.addStringField("fileName", Message.newSaveAs());
        form.parse();
        _fileName = form.stringField("fileName");
        _receiver.saveAs(_fileName);
      }
      else {
        _receiver.save();
      }
    } catch (IOException | MissingFileAssociationException e) {
      //DO NOTHING (ASK PROF)
      e.printStackTrace();
    }
  }
}
