package rus.tutby.exception;


public class NoInternetException extends Exception {

    public NoInternetException() { super(); }

    public NoInternetException(String message) { super(message); }

    public NoInternetException(String message, Throwable cause) { super(message, cause); }

    public NoInternetException(Throwable cause) { super(cause); }

}
