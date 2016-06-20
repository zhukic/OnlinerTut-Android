package rus.tutby.repository;

import java.util.List;

import rus.tutby.entity.News;
import rx.Observable;

/**
 * Created by RUS on 06.06.2016.
 */
public class NewsRepository implements IRepository{

    NewsRepository() {

    }


    @Override
    public Observable<List<News>> getAllNews() {
        return null;
    }

    @Override
    public Observable<News> getNewsById(int id) {
        return null;
    }
}
