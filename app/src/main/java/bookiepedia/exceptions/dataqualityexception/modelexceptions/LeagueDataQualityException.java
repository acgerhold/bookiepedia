package bookiepedia.exceptions.dataqualityexception.modelexceptions;

import bookiepedia.exceptions.dataqualityexception.DataQualityException;

public class LeagueDataQualityException extends DataQualityException {
    private static final long serialVersionUID = 6901514791358051389L;

    /**
     * Exception with no message or cause.
     */
    public LeagueDataQualityException() {
        super();
    }

    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public LeagueDataQualityException(String message) {
        super(message);
    }

    /**
     * Exception with no message, but with a cause.
     * @param cause The original throwable resulting in this exception.
     */
    public LeagueDataQualityException(Throwable cause) {
        super(cause);
    }

    /**
     * Exception with message and cause.
     * @param message A descriptive message for this exception.
     * @param cause The original throwable resulting in this exception.
     */
    public LeagueDataQualityException(String message, Throwable cause) {
        super(message, cause);
    }
}
