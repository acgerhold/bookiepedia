package bookiepedia.dependencies;

import bookiepedia.activities.*;
import bookiepedia.activities.requests.GetEventsForScheduleRequest;
import bookiepedia.dynamodb.EspnDAO.EspnDAO;
import bookiepedia.dynamodb.assets.TeamDAO;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {DAOModule.class, EspnModule.class})
public interface ServiceComponent {

    GetScheduleActivity provideGetScheduleActivity();

    FetchScheduleActivity provideFetchScheduleActivity();

    GetEventsForScheduleActivity provideGetEventsForScheduleActivity();

    AddBetToHistoryActivity provideAddBetToHistoryActivity();

    GetBetsForHistoryActivity provideGetBetsForHistoryActivity();

    RemoveBetFromHistoryActivity provideRemoveBetFromHistoryActivity();

    EspnDAO provideEspnDAO();
}
