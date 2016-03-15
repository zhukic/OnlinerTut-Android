package rus.tutby.mvp.presenter;

import android.os.AsyncTask;

import java.sql.SQLException;

import rus.tutby.MyApplication;
import rus.tutby.database.DatabaseManager;
import rus.tutby.mvp.model.FeedBuildDate;
import rus.tutby.mvp.model.News;
import rus.tutby.parser.rssparser.RssParser;

/**
 * Created by RUS on 11.03.2016.
 */

public class ReadRssTask extends AsyncTask<String, Void, Feed> {

    private static final String TAG = "TAG";

    private ParseListener parseListener;

    private Feed feed;

    ReadRssTask(ParseListener parseListener, Feed feed) {
        this.parseListener = parseListener;
        this.feed = feed;
    }

    @Override
    protected Feed doInBackground(String... params) {
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

        final String url = params[0];
        final String category = params[1];

        RssParser rssParser = new RssParser(url, MyApplication.getProvider());
        String lastBuildDate = rssParser.getLastBuildDate();

        if(!lastBuildDate.equals(FeedBuildDate.getBuildDate(category))) {
            for(int i = rssParser.size() - 1; i >= 0; i--) {
                News news = rssParser.getItem(i);
                news.setCategory(category);
                try {
                    feed.addOrUpdate(news);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            FeedBuildDate.changeBuildDate(category, lastBuildDate);
        } else {
            feed.setNewsList(DatabaseManager.getNewsListFromDatabase(category, MyApplication.getProvider()));
        }
        return feed;
    }

    @Override
    protected void onPostExecute(Feed result) {
        super.onPostExecute(result);

        parseListener.onFeedParsed(result);
    }
}
