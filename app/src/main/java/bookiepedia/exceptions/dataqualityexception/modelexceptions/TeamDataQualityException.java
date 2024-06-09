package bookiepedia.exceptions.dataqualityexception.modelexceptions;

import bookiepedia.exceptions.dataqualityexception.DataQualityException;

public class TeamDataQualityException extends DataQualityException {
    private static final long serialVersionUID = 4585825213860116379L;

    /**
     * Exception with no message or cause.
     */
    public TeamDataQualityException() {
        super();
    }

    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public TeamDataQualityException(String message) {
        super(message);
    }

    /**
     * Exception with no message, but with a cause.
     * @param cause The original throwable resulting in this exception.
     */
    public TeamDataQualityException(Throwable cause) {
        super(cause);
    }

    /**
     * Exception with message and cause.
     * @param message A descriptive message for this exception.
     * @param cause The original throwable resulting in this exception.
     */
    public TeamDataQualityException(String message, Throwable cause) {
        super(message, cause);
    }
}
