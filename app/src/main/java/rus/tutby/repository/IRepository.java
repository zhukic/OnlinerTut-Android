package rus.tutby.repository;

import rus.tutby.entity.News;
import rx.Observable;

/**
 * Created by RUS on 20.06.2016.
 */
public interface IRepository {

    Observable<News> getAllNews(final String url);

    Observable<News> getNewsById(final int id);

}
