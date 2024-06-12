package bookiepedia.dependencies;

import bookiepedia.dynamodb.EspnDAO.EspnDAO;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class EspnModule {

    @Singleton
    @Provides
    public EspnDAO provideEspnDAO(DynamoDBMapper dynamoDBMapper) {
        return new EspnDAO(dynamoDBMapper);
    }
}
