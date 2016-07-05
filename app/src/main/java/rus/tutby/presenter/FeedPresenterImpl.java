package rus.tutby.presenter;

import android.os.SystemClock;
import android.util.Log;

import java.util.List;

import rus.tutby.entity.News;
import rus.tutby.interactors.GetNewsListUseCase;
import rus.tutby.ui.FeedView;
import rus.tutby.utils.Logger;
import rx.Subscriber;

/**
 * Created by RUS on 11.03.2016.
 */
public class FeedPresenterImpl implements FeedPresenter {

    private static final String TAG = "TAG";

    private FeedView feedView;
    private GetNewsListUseCase getNewsListUseCase;

    private String url;
    private String category;

    public FeedPresenterImpl(FeedView feedView, String url, String category) {
        this.feedView = feedView;
        this.url = url;
        this.category = category;

        getNewsListUseCase = new GetNewsListUseCase();
    }

    @Override
    public void parse() {
        if(feedView != null) {
            feedView.showRefresh();
            getNewsListUseCase.downloadNews(url, new NewsListSubscriber());
        }
    }

    private void showNewsList(List<News> newsList) {
        feedView.setFeed(newsList);
    }

    private void hideRefresh() {
        this.feedView.hideRefresh();
    }

    private void onError(String message) {
        feedView.onError(message);
        feedView.hideRefresh();
    }

    @Override
    public void onNewsClicked(int position) {

    }

    @Override
    public void onDestroy() {
        this.feedView = null;
        getNewsListUseCase.unsubscribe();
    }

    private final class NewsListSubscriber extends Subscriber<List<News>> {

        @Override
        public void onCompleted() {
            FeedPresenterImpl.this.hideRefresh();
        }

        @Override
        public void onError(Throwable e) {
            FeedPresenterImpl.this.onError(e.getMessage());
        }

        @Override
        public void onNext(List<News> newsList) {
            FeedPresenterImpl.this.showNewsList(newsList);
        }
    }
}
