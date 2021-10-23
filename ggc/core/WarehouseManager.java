package ggc.core;

//FIXME import classes (cannot import from pt.tecnico or ggc.app)

import java.io.Serializable;
import java.io.IOException;
import java.io.FileNotFoundException;

import ggc.core.exception.BadEntryException;
import ggc.core.exception.ImportFileException;
import ggc.core.exception.UnavailableFileException;
import ggc.core.exception.MissingFileAssociationException;
import ggc.core.exception.InvalidDateException;

/** Façade for access. */
public class WarehouseManager {

  /** Name of file storing current warehouse. */
  private String _filename = "";

  /** The warehouse itself. */
  private Warehouse _warehouse = new Warehouse();

  //FIXME define other attributes
  //FIXME define constructor(s)
  public WarehouseManager() {
  }
  //FIXME define other methods

  /**
   * @@throws IOException
   * @@throws FileNotFoundException
   * @@throws MissingFileAssociationException
   */

  public void open() {

  }

  public void save() throws IOException, FileNotFoundException, MissingFileAssociationException {
    //FIXME implement serialization method
  }

  /**
   * @@param filename
   * @@throws MissingFileAssociationException
   * @@throws IOException
   * @@throws FileNotFoundException
   */
  public void saveAs(String filename) throws MissingFileAssociationException, FileNotFoundException, IOException {
    _filename = filename;
    save();
  }

  /**
   * @@param filename
   * @@throws UnavailableFileException
   */
  public void load(String filename) throws UnavailableFileException, ClassNotFoundException  {
    //FIXME implement serialization method
  }

  /**
   * @param textfile
   * @throws ImportFileException
   */
  public void importFile(String textfile) throws ImportFileException {
    try {
      _warehouse.importFile(textfile);
    } catch (IOException | BadEntryException /* FIXME maybe other exceptions */ e) {
      throw new ImportFileException(textfile, e);
    }
  }

  public void setLoadFile(String loadFile) {
    _loadFile = loadFile;
  }

  public String getLoadFile() {
    return _loadFile;
  }

  public boolean hasLoadFile() {
    return !_loadFile.isEmpty();
  }

  public int currentDate() {
    return _warehouse.getDate();
  }

  public void requestDaysToAdvance(int days) throws InvalidDateException {
    try {
      _warehouse.advanceDays(days);
    } catch (InvalidDateException ide) { throw ide; }
  }

  // Gestão e consulta de dados da aplicação???

  public int currentBalance() {
    return _warehouse.getBalance();
  }

  /*public int contabilisticBalance() {
    return _warehouse.getContabilisticBalance(); //definir metodo
  }*/

  /*public String getAllProducts() {
    return _warehouse.getAllProducts(); // definir metodo
  }*/



}
