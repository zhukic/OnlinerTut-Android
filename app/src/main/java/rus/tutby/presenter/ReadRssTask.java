package rus.tutby.presenter;

import android.os.AsyncTask;
import android.util.Log;

import java.sql.SQLException;

import rus.tutby.App;
import rus.tutby.database.DatabaseManager;
import rus.tutby.entity.FeedBuildDate;
import rus.tutby.entity.News;
import rus.tutby.parser.rssparser.RssParser;
import rus.tutby.utils.Time;

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

        final String url = params[0];
        final String category = params[1];

        RssParser rssParser = new RssParser(url);
        String lastBuildDate = rssParser.getLastBuildDate();

        if(!lastBuildDate.equals(FeedBuildDate.getBuildDate(category))) {
            Time time = new Time();

            for(int i = rssParser.size() - 1; i >= 0; i--) {
                News news = rssParser.getItem(i);
                news.setCategory(category);
                try {
                    if(i < 20 || rssParser.size() < 20) {
                        feed.addOrUpdate(news, true);
                    } else feed.addOrUpdate(news, false);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            FeedBuildDate.changeBuildDate(category, lastBuildDate);

            Log.i(TAG, "parse time " + String.valueOf(time.getTime()));
        } else {
            Time time = new Time();

            feed.setNewsList(DatabaseManager.getNewsListFromDatabase(category, App.getProvider()));

            Log.i(TAG, "getFromBD time " + String.valueOf(time.getTime()));

        }
        return feed;
    }

    @Override
    protected void onPostExecute(Feed result) {
        super.onPostExecute(result);

        parseListener.onFinishedParse(result);
    }
}
