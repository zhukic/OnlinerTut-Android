package rus.tutby.interactors;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import rus.tutby.App;
import rus.tutby.entity.News;
import rus.tutby.entity.Provider;
import rus.tutby.parser.rssparser.RssParser;
import rus.tutby.presenter.Feed;
import rus.tutby.presenter.FeedPresenterImpl;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Created by RUS on 15.06.2016.
 */
public class GetNewsListUseCase {

    private Subscription subscrition = Subscriptions.empty();

    public GetNewsListUseCase() {}

    public void downloadNews(final String url, Subscriber subscriber) {
        this.subscrition = Observable.create(new Observable.OnSubscribe<Feed>() {
            @Override
            public void call(Subscriber<? super Feed> subscriber) {
                RssParser rssParser = new RssParser(url, App.getProvider());
                subscriber.onNext(rssParser.getFeed());
                subscriber.onCompleted();
            }
        })
                .map(new Func1<Feed, List<News>>() {
                    @Override
                    public List<News> call(Feed feed) {
                        return feed.getFeedToShow();
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    public void unsubscribe() {
        this.subscrition.unsubscribe();
    }
}
