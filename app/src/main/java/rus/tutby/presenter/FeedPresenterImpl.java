package rus.tutby.presenter;

import android.util.Log;

import java.util.List;

import rus.tutby.entity.News;
import rus.tutby.interactors.GetNewsListUseCase;
import rus.tutby.ui.FeedView;
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
    public void parse(boolean hasInternet) {

        //Log.i(TAG, "parse " + category);

        if(feedView != null) {
            feedView.showRefresh();

            if(hasInternet) {
                getNewsListUseCase.downloadNews(url, new NewsListSubscriber());
            } else {
                this.onError("No internet connection!");
                //onParseFinished();
            }
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
    public void onDestroy() {
        getNewsListUseCase.unsubscribe();
        feedView = null;
    }

    @Override
    public void onNewsClicked(int position) {

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
            /*for (News news: newsList) {
                Log.d(TAG, news.getNumber() + "");
            }*/
            FeedPresenterImpl.this.showNewsList(newsList);
        }
    }
}
