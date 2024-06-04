package bookiepedia.exceptions.modelexceptions;

import bookiepedia.exceptions.DataQualityException;

public class EventDataQualityException extends DataQualityException {

    private static final long serialVersionUID = 790202017186254756L;

    public EventDataQualityException() {
        super();
    }

    public EventDataQualityException(String message) {
        super(message);
    }

    public EventDataQualityException(Throwable cause) {
        super(cause);
    }

    public EventDataQualityException(String message, Throwable cause) {
        super(message, cause);
    }
}
