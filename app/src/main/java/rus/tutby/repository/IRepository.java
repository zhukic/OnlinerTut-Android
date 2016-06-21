package rus.tutby.repository;

import java.util.List;

import rus.tutby.entity.News;
import rus.tutby.presenter.Feed;
import rx.Observable;

/**
 * Created by RUS on 20.06.2016.
 */
public interface IRepository {

    Observable<Feed> getAllNews(final String url);

    Observable<News> getNewsById(final int id);

}
