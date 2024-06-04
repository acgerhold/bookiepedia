package bookiepedia.exceptions.dataqualityexception;

public class DataQualityException extends RuntimeException {

    private static final long serialVersionUID = -4783354455577682266L;

    public DataQualityException() {
        super();
    }

    public DataQualityException(String message) {
        super(message);
    }

    public DataQualityException(Throwable cause) {
        super(cause);
    }

    public DataQualityException(String message, Throwable cause) {
        super(message, cause);
    }
}
