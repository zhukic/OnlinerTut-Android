package rus.tutby.repository;

import android.content.Context;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import rus.tutby.App;
import rus.tutby.database.DatabaseManager;
import rus.tutby.entity.News;
import rus.tutby.parser.rssparser.RssParser;
import rus.tutby.presenter.Feed;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by RUS on 06.06.2016.
 */
public class NewsRepository implements IRepository {

    private Context context;

    @Inject
    NewsRepository(Context context) {
        this.context = context;
    }

    @Override
    public Observable<Feed> getAllNews(final String url) {
        return Observable.create(new Observable.OnSubscribe<Feed>() {
            @Override
            public void call(Subscriber<? super Feed> subscriber) {
                RssParser rssParser = new RssParser(url, App.getProvider());
                DatabaseManager.addToDatabase(rssParser.getFeed().getFeedToShow());
                subscriber.onNext(rssParser.getFeed());
                subscriber.onCompleted();
            }
        });
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
