package bookiepedia.dependencies;

import bookiepedia.activities.FetchScheduleActivity;
import bookiepedia.activities.GetScheduleActivity;
import bookiepedia.dynamodb.EspnDAO.EspnDAO;
import bookiepedia.dynamodb.assets.TeamDAO;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {DAOModule.class, EspnModule.class})
public interface ServiceComponent {

    GetScheduleActivity provideGetScheduleActivity();

    FetchScheduleActivity provideFetchScheduleActivity();

    EspnDAO provideEspnDAO();
}
