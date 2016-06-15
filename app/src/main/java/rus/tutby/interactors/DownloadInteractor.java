package rus.tutby.interactors;

import org.jetbrains.annotations.NotNull;

import rus.tutby.entity.Provider;
import rus.tutby.parser.rssparser.RssParser;
import rus.tutby.presenter.Feed;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by RUS on 15.06.2016.
 */
public class DownloadInteractor implements IDownloadInteractor {

    private String url;

    public DownloadInteractor(String url) {
        this.url = url;
    }


    @Override
    public Observable<Feed> downloadNews(@NotNull OnFinishedListener onFinishedListener) {
        return Observable.create(new Observable.OnSubscribe<Feed>() {
            @Override
            public void call(Subscriber<? super Feed> subscriber) {
                RssParser rssParser = new RssParser(url, Provider.TUT);
                subscriber.onNext(rssParser.getFeed());
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }
}
