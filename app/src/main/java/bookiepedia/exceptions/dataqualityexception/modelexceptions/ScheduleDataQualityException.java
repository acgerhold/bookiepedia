package bookiepedia.exceptions.dataqualityexception.modelexceptions;

import bookiepedia.exceptions.dataqualityexception.DataQualityException;

public class ScheduleDataQualityException extends DataQualityException {

    private static final long serialVersionUID = 9087239402997469242L;

    /**
     * Exception with no message or cause.
     */
    public ScheduleDataQualityException() {
        super();
    }

    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public ScheduleDataQualityException(String message) {
        super(message);
    }

    /**
     * Exception with no message, but with a cause.
     * @param cause The original throwable resulting in this exception.
     */
    public ScheduleDataQualityException(Throwable cause) {
        super(cause);
    }

    /**
     * Exception with message and cause.
     * @param message A descriptive message for this exception.
     * @param cause The original throwable resulting in this exception.
     */
    public ScheduleDataQualityException(String message, Throwable cause) {
        super(message, cause);
    }
}
