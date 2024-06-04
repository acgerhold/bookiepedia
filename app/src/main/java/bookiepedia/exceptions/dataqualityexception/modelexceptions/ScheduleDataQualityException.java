package bookiepedia.exceptions.dataqualityexception.modelexceptions;

import bookiepedia.exceptions.dataqualityexception.DataQualityException;

public class ScheduleDataQualityException extends DataQualityException {

    private static final long serialVersionUID = 9087239402997469242L;

    public ScheduleDataQualityException() {
        super();
    }

    public ScheduleDataQualityException(String message) {
        super(message);
    }

    public ScheduleDataQualityException(Throwable cause) {
        super(cause);
    }

    public ScheduleDataQualityException(String message, Throwable cause) {
        super(message, cause);
    }
}
