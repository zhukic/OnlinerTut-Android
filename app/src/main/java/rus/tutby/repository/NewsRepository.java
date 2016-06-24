package rus.tutby.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import rus.tutby.App;
import rus.tutby.database.DatabaseManager;
import rus.tutby.entity.News;
import rus.tutby.parser.rssparser.RssParser;
import rus.tutby.presenter.Feed;
import rus.tutby.repository.datasource.CloudDataStore;
import rx.Observable;
import rx.Single;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by RUS on 06.06.2016.
 */
public class NewsRepository implements IRepository {

    public NewsRepository() {
    }

    @Override
    public Observable<News> getAllNews(final String url) {
        return new CloudDataStore().userEntityList(url);

    }

    @Override
    public Observable<News> getNewsById(final int id) {
        return Observable.create(new Observable.OnSubscribe<News>() {
            @Override
            public void call(Subscriber<? super News> subscriber) {
                try {
                    final News news = App.getNewsDao().queryForId(id);
                    subscriber.onNext(news);
                } catch (SQLException e) {
                    subscriber.onError(e);
                }
            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
