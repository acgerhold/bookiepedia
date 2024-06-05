package bookiepedia.dynamodb.models.assets;

public class Bookmaker {

    private String bookmakerId;
    private String bookmakerName;
    private String bookmakerLogo;

    // GETTERS

    public String getBookmakerId() {
        return bookmakerId;
    }

    public String getBookmakerName() {
        return bookmakerName;
    }

    public String getBookmakerLogo() {
        return bookmakerLogo;
    }

    // SETTERS

    public void setBookmakerId(String bookmakerId) {
        this.bookmakerId = bookmakerId;
    }

    public void setBookmakerName(String bookmakerName) {
        this.bookmakerName = bookmakerName;
    }

    public void setBookmakerLogo(String bookmakerLogo) {
        this.bookmakerLogo = bookmakerLogo;
    }
}
