package bookiepedia.exceptions.dataqualityexception.modelexceptions;

import bookiepedia.exceptions.dataqualityexception.DataQualityException;

public class EventDataQualityException extends DataQualityException {

    private static final long serialVersionUID = 790202017186254756L;

    /**
     * Exception with no message or cause.
     */
    public EventDataQualityException() {
        super();
    }

    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public EventDataQualityException(String message) {
        super(message);
    }

    /**
     * Exception with no message, but with a cause.
     * @param cause The original throwable resulting in this exception.
     */
    public EventDataQualityException(Throwable cause) {
        super(cause);
    }

    /**
     * Exception with message and cause.
     * @param message A descriptive message for this exception.
     * @param cause The original throwable resulting in this exception.
     */
    public EventDataQualityException(String message, Throwable cause) {
        super(message, cause);
    }
}
