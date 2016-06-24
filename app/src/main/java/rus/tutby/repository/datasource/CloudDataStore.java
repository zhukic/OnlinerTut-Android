package rus.tutby.repository.datasource;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;

import rus.tutby.App;
import rus.tutby.entity.News;
import rus.tutby.parser.rssparser.RssParser;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by RUS on 20.06.2016.
 */
public class CloudDataStore implements INewsStore {

    @Inject
    Context context;

    public CloudDataStore() {}

    @Override
    public Observable<News> userEntityList(final String url) {
        return Observable.create(new Observable.OnSubscribe<News>() {
            @Override
            public void call(Subscriber<? super News> subscriber) {
                RssParser rssParser = new RssParser(url);
                for(int i = 0; i < rssParser.size(); i++) {
                    subscriber.onNext(rssParser.getItem(i).setNumber(App.getCounter()));
                }
                subscriber.onCompleted();
            }
        });
    }

}
