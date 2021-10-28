package ggc.core.exception;

public class UnavailableFileException extends Exception {

	private static final long serialVersionUID = 202009192006L;
	String _filename;

	public UnavailableFileException(String filename) {
	  _filename = filename;
	}

	public String getFilename() {
		return _filename;
	}

}
