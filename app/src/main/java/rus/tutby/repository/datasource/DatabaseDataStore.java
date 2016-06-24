package rus.tutby.repository.datasource;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;

import rus.tutby.entity.News;
import rx.Observable;

/**
 * Created by RUS on 20.06.2016.
 */
public class DatabaseDataStore implements INewsStore {

    @Inject
    Context context;

    @Override
    public Observable<News> userEntityList(String url) {
        return null;
    }

    public Observable<News> userEntityDetails(int userId) {
        return null;
    }
}
