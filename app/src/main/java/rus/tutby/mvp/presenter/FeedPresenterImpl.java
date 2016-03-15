package rus.tutby.mvp.presenter;

import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import rus.tutby.MyApplication;
import rus.tutby.database.DatabaseManager;
import rus.tutby.mvp.model.News;
import rus.tutby.mvp.view.FeedView;

/**
 * Created by RUS on 11.03.2016.
 */
public class FeedPresenterImpl implements FeedPresenter, ParseListener {

    private static final String TAG = "TAG";

    private FeedView feedView;

    private String url;
    private String category;

    private Feed feed;

    public FeedPresenterImpl(FeedView feedView, String url, String category) {
        this.feedView = feedView;
        this.url = url;
        this.category = category;

        feed = new Feed();
    }

    @Override
    public void parse(boolean hasInternet) {

        Log.i(TAG, "parse " + category);

        if(feedView != null) {
            if(hasInternet) {
                feedView.showRefresh();

                ReadRssTask readRssTask = new ReadRssTask(this, feed);
                readRssTask.execute(url, category);
            } else {
                feedView.onError("No internet connection!");
                //onParseFinished();
            }
        }
    }

    @Override
    public void onFeedParsed(Feed newFeed) {

        Log.i(TAG, "onFeedParsed " + category);

        if(newFeed != null) {
            this.feed = newFeed;
        }
        if(feedView != null) {
            feedView.hideRefresh();
            feedView.setFeed(feed.getFeedToShow());
        }
    }

    @Override
    public void upload() {

        Log.i(TAG, "upload " + category);

        if(feedView != null) {

            feedView.showLowProgress();

            feed.uploadFeedToShow();

            feedView.notifyAdapter();
            feedView.hideLowProgress();
        }
    }

    @Override
    public void onDestroy() {
        feedView = null;
    }

    @Override
    public void onNewsClicked(int position) {
        feedView.openNews(feed.getNews(position).getId());
    }
}
