package rus.tutby.mvp.presenter;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import rus.tutby.MyApplication;
import rus.tutby.database.DatabaseManager;
import rus.tutby.mvp.model.News;

/**
 * Created by RUS on 11.03.2016.
 */

public class UploadFeedTask extends AsyncTask<Void, Void, Void> {

    private FeedPresenter feedPresenter;
    private List<News> newsList;

    public UploadFeedTask(FeedPresenter feedPresenter, List<News> newsList) {
        this.feedPresenter = feedPresenter;
        this.newsList = newsList;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        feedPresenter.onUploadStarted();
    }

    @Override
    protected Void doInBackground(Void... params) {
        final int size = newsList.size();

        final ArrayList<News> newsFromBase = DatabaseManager
                .getNewsListFromDatabase(newsList.get(0).getCategory(), MyApplication.getProvider());

        for(int i = size; i < size + 20; i++) {
            if(i == newsFromBase.size()) {
                break;
            } else {
                newsList.add(newsFromBase.get(i));
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        feedPresenter.onUploadFinished();
    }
}
