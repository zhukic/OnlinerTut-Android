package rus.tutby.repository.datasource;

import java.util.List;

import rus.tutby.entity.News;
import rx.Observable;

/**
 * Created by RUS on 20.06.2016.
 */
public interface INewsStore {

    Observable<News> userEntityList(String url);

}
