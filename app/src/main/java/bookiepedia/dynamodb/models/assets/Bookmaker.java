package bookiepedia.dynamodb.models.assets;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Bookmaker")
public class Bookmaker {

    private String bookmakerId;
    private String bookmakerName;
    private String bookmakerLogo;

    // GETTERS

    @DynamoDBHashKey(attributeName = "bookmakerId")
    public String getBookmakerId() {
        return bookmakerId;
    }

    @DynamoDBAttribute(attributeName = "bookmakerName")
    public String getBookmakerName() {
        return bookmakerName;
    }

    @DynamoDBAttribute(attributeName = "bookmakerLogo")
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
