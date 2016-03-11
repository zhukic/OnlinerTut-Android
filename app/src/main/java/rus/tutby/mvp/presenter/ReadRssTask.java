package rus.tutby.mvp.presenter;

import android.os.AsyncTask;

import java.sql.SQLException;
import java.util.ArrayList;
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

public class ReadRssTask extends AsyncTask<String, Void, Void> {

    private FeedPresenter feedPresenter;
    private List<News> newsList;

    ReadRssTask(FeedPresenter feedPresenter, List<News> newsList) {
        this.feedPresenter = feedPresenter;
        this.newsList = newsList;
    }

    private NoInternetException noInternetException;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        feedPresenter.onParseStarted();
    }

    @Override
    protected Void doInBackground(String... params) {

        ArrayList<News> newsFromRss = new ArrayList<>();

        String url = params[0];
        String category = params[1];

        try {
            RssParser rssParser = new RssParser(url, MyApplication.getProvider());

            String lastBuildDate = rssParser.getLastBuildDate();

            if(!lastBuildDate.equals(FeedBuildDate.getBuildDate(category))) {
                for(int i = 0; i < rssParser.size(); i++) {
                    News news = rssParser.getItem(i);
                    news.setCategory(category);
                    if(DatabaseManager.contains(news)) {
                        DatabaseManager.update(news);
                        break;
                    } else newsFromRss.add(news);
                }
            } else FeedBuildDate.changeBuildDate(category, lastBuildDate);
            DatabaseManager.addToDatabase(newsFromRss);
        } catch (NoInternetException e) {
            noInternetException = e;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ArrayList<News> newsFromBase = DatabaseManager
                .getNewsListFromDatabase(category, MyApplication.getProvider());

        final int size = newsList.size();

        for(int i = size; i < 20; i++) {
            if(i == newsFromBase.size()) {
                break;
            }
            newsList.add(newsFromBase.get(i));
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        feedPresenter.onParseFinished(newsList);
    }
}
