package util;
public class AppException extends Exception {
    public AppException(String msg){ super(msg); }
    public AppException(String msg, Throwable t){ super(msg, t); }
}
