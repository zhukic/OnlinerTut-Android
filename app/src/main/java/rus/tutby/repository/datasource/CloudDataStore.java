package rus.tutby.repository.datasource;

import java.util.List;

import rus.tutby.entity.News;
import rx.Observable;

/**
 * Created by RUS on 20.06.2016.
 */
public class CloudDataStore implements INewsStore {

    CloudDataStore() {}

    @Override
    public Observable<List<News>> userEntityList() {
        return null;
    }

    @Override
    public Observable<News> userEntityDetails(int userId) {
        return null;
    }

}
