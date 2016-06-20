package rus.tutby.repository;

import java.util.List;

import rus.tutby.entity.News;
import rx.Observable;

/**
 * Created by RUS on 20.06.2016.
 */
public interface IRepository {

    Observable<List<News>> getAllNews();

    Observable<News> getNewsById(int id);

}
