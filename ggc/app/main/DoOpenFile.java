package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
//FIXME import classes
import ggc.app.exception.FileOpenFailedException;
import ggc.core.exception.UnavailableFileException;
import ggc.app.main.Message;


/**
 * Open existing saved state.
 */
class DoOpenFile extends Command<WarehouseManager> {

  private String _fileName;

  /** @param receiver */
  DoOpenFile(WarehouseManager receiver) {
    super(Label.OPEN, receiver);
    //FIXME maybe add command fields
    addStringField("fileName", Message.openFile());
  }

  @Override
  public final void execute() throws CommandException, FileOpenFailedException {
    try {
      //FIXME implement command
      _fileName = stringField("fileName");
      _receiver.load(_fileName);
    } catch (UnavailableFileException ufe) {
      throw new FileOpenFailedException(ufe.getFilename());
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

}
