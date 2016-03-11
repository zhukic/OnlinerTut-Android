package rus.tutby.mvp.presenter;

import android.os.AsyncTask;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import rus.tutby.MyApplication;
import rus.tutby.database.DatabaseManager;
import rus.tutby.exception.NoInternetException;
import rus.tutby.mvp.model.FeedBuildDate;
import rus.tutby.mvp.model.News;
import rus.tutby.mvp.view.FeedView;
import rus.tutby.parser.rssparser.RssParser;

/**
 * Created by RUS on 11.03.2016.
 */
public class FeedPresenterImpl implements FeedPresenter, OnFinishedListener {

    private FeedView feedView;

    private String url;
    private String category;

    private LinkedList<News> newsList;

    public FeedPresenterImpl(FeedView feedView, String url, String category) {
        this.feedView = feedView;
        this.url = url;
        this.category = category;

        newsList = new LinkedList<>();

        parse();
    }

    @Override
    public void parse() {
        ReadRssTask readRssTask = new ReadRssTask(this, newsList);
        readRssTask.execute(url, category);
    }

    @Override
    public void onParseStarted() {
        if(feedView != null) {
            feedView.showRefresh();
        }
    }

    @Override
    public void onParseFinished(List<News> list) {

        newsList = (LinkedList<News>) list;

        if(feedView != null) {
            feedView.hideRefresh();
            feedView.setItems(newsList);
        }

    }

    @Override
    public void onResume() {

    }

    @Override
    public void upload() {
        UploadFeedTask uploadFeedTask = new UploadFeedTask(this, newsList);
        uploadFeedTask.execute();
    }

    @Override
    public void onUploadStarted() {
        feedView.showLowProgress();
    }

    @Override
    public void onUploadFinished() {
        if(feedView != null) {
            feedView.notifyAdapter();
            feedView.hideLowProgress();
        }

    }

    @Override
    public News getNewsAtPosition(int position) {
        return newsList.get(position);
    }

    @Override
    public void onDestroy() {
        feedView = null;
    }

    @Override
    public void onFinished(List<News> list) {

    }
}
