package bookiepedia.exceptions.modelexceptions;

import bookiepedia.exceptions.DataQualityException;

public class TeamDataQualityException extends DataQualityException {
    private static final long serialVersionUID = 4585825213860116379L;

    public TeamDataQualityException() {
        super();
    }

    public TeamDataQualityException(String message) {
        super(message);
    }

    public TeamDataQualityException(Throwable cause) {
        super(cause);
    }

    public TeamDataQualityException(String message, Throwable cause) {
        super(message, cause);
    }
}
