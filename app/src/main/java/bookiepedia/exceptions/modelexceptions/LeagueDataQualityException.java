package bookiepedia.exceptions.modelexceptions;

import bookiepedia.exceptions.DataQualityException;

public class LeagueDataQualityException extends DataQualityException {
    private static final long serialVersionUID = 6901514791358051389L;

    public LeagueDataQualityException() {
        super();
    }

    public LeagueDataQualityException(String message) {
        super(message);
    }

    public LeagueDataQualityException(Throwable cause) {
        super(cause);
    }

    public LeagueDataQualityException(String message, Throwable cause) {
        super(message, cause);
    }
}
