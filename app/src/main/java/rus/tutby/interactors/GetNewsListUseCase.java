package rus.tutby.interactors;

import rus.tutby.repository.NewsRepository;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
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
