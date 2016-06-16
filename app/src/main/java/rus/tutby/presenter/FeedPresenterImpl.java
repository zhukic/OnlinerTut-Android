package rus.tutby.presenter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import rus.tutby.entity.News;
import rus.tutby.interactors.GetNewsListUseCase;
import rus.tutby.interactors.IDownloadInteractor;
import rus.tutby.ui.FeedView;
import rx.Subscriber;

/**
 * Created by RUS on 11.03.2016.
 */
public class FeedPresenterImpl implements FeedPresenter, ParseListener {

    private static final String TAG = "TAG";

    private FeedView feedView;
    private GetNewsListUseCase getNewsListUseCase;

    private String url;
    private String category;

    private Feed feed;

    public FeedPresenterImpl(FeedView feedView, String url, String category) {
        this.feedView = feedView;
        this.url = url;
        this.category = category;

        feed = new Feed();
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

    @Override
    public void onFinishedParse(Feed newFeed) {

        //Log.i(TAG, "onFinishedParse " + category);

        if(newFeed != null) {
            this.feed = newFeed;
        }
        if(feedView != null) {
            feedView.hideRefresh();
            feedView.setFeed(feed.getFeedToShow());
            //Log.i(TAG, String.valueOf(feed.getSize()));
        }
    }

    @Override
    public void upload() {

        //Log.i(TAG, "upload " + category);

        if(feedView != null) {

            feedView.showLowProgress();

            feed.uploadFeedToShow();

            feedView.notifyAdapter();
            feedView.hideLowProgress();
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
        feedView = null;
    }

    @Override
    public void onNewsClicked(int position) {
        feedView.openNews(feed.getNews(position).getId());
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
