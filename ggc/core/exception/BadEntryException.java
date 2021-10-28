package ggc.core.exception;

public class BadEntryException extends Exception {

  private static final long serialVersionUID = 201409301048L;

  private String _entrySpecification;

  public BadEntryException(String entrySpecification) {
    _entrySpecification = entrySpecification;
  }

  public BadEntryException(String entrySpecification, Exception cause) {
    super(cause);
    _entrySpecification = entrySpecification;
  }

  public String getEntrySpecification() {
    return _entrySpecification;
  }

}
