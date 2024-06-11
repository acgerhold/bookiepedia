package bookiepedia.dependencies;

import bookiepedia.activities.GetScheduleActivity;
import bookiepedia.dynamodb.assets.TeamDAO;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = DAOModule.class)
public interface ServiceComponent {

    GetScheduleActivity provideGetScheduleActivity();
}
