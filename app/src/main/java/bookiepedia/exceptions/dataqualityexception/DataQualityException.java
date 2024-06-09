package bookiepedia.exceptions.dataqualityexception;

public class DataQualityException extends RuntimeException {

    private static final long serialVersionUID = -4783354455577682266L;

    /**
     * Exception with no message or cause.
     */
    public DataQualityException() {
        super();
    }

    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public DataQualityException(String message) {
        super(message);
    }

    /**
     * Exception with no message, but with a cause.
     * @param cause The original throwable resulting in this exception.
     */
    public DataQualityException(Throwable cause) {
        super(cause);
    }

    /**
     * Exception with message and cause.
     * @param message A descriptive message for this exception.
     * @param cause The original throwable resulting in this exception.
     */
    public DataQualityException(String message, Throwable cause) {
        super(message, cause);
    }
}
