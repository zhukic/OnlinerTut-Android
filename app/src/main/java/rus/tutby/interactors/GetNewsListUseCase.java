package rus.tutby.interactors;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import dagger.ObjectGraph;
import rus.tutby.App;
import rus.tutby.di.AppModule;
import rus.tutby.entity.News;
import rus.tutby.entity.Provider;
import rus.tutby.parser.rssparser.RssParser;
import rus.tutby.presenter.Feed;
import rus.tutby.presenter.FeedPresenterImpl;
import rus.tutby.repository.NewsRepository;
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

    private Subscription subscription = Subscriptions.empty();

    NewsRepository newsRepository;

    public GetNewsListUseCase() {
        newsRepository = new NewsRepository();
    }

    public void downloadNews(String url, Subscriber subscriber) {
        this.subscription = this.newsRepository.getAllNews(url)
                .toList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    public void unsubscribe() {
        this.subscription.unsubscribe();
    }
}
