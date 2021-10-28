package ggc.core.exception;

public class ImportFileException extends Exception {

  private static final long serialVersionUID = 201708301010L;

  public ImportFileException() {
    // do nothing
  }

  public ImportFileException(String description) {
    super(description);
  }

  public ImportFileException(String importFile, Exception cause) {
    super("Erro em ficheiro de import: " + importFile, cause);
  }

}
