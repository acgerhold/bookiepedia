package bookiepedia.dependencies;

import bookiepedia.dynamodb.EspnDAO.EspnDAO;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class EspnModule {

    @Singleton
    @Provides
    public EspnDAO provideEspnDAO() {
        return new EspnDAO();
    }
}
