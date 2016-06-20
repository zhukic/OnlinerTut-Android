package rus.tutby.presenter;

import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

import rus.tutby.App;
import rus.tutby.database.DatabaseManager;
import rus.tutby.entity.News;

/**
 * Created by RUS on 13.03.2016.
 */
public class Feed {

    private static final String TAG = "TAG";

    private ArrayList<News> feed;

    private ArrayList<News> feedToShow;

    public Feed() {
        feed = new ArrayList<>();
        feedToShow = new ArrayList<>();
    }

    public int getSize() {
        return feed.size();
    }

    private News getEqualNews(News news) {
        for(News i : feed) {
            if(i.getCategory().equals(news.getCategory()) && i.getLink().equals(news.getLink()))
                return i;
        }
        return null;
    }

    public void addOrUpdate(News news, boolean addToDB) throws SQLException {
        News equalNews = getEqualNews(news);
        if(equalNews != null) {
            if(!equalNews.getDate().equals(news.getDate())) {
                Log.i(TAG, "update");
                DatabaseManager.update(news);
                feed.set(feed.indexOf(equalNews), news);
            }
        } else {
            if(addToDB) {
                DatabaseManager.addToDatabase(news);
            }
            feed.add(0, news);
            if(feed.size() == 101) {
                App.getNewsDao().deleteById(feed.get(feed.size() - 1).getId());
                feed.remove(feed.size() - 1);
            }
        }
    }

    public News getNews(int position) {
        return feed.get(position);
    }

    public void setNewsList(ArrayList<News> newsList) {
        this.feed = newsList;
    }

    public ArrayList<News> uploadFeedToShow() {
        final int size = feedToShow.size();
        for(int i = size; i < size + 20; i++) {
            if(i != feed.size()) {
                final News news = feed.get(i);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DatabaseManager.addToDatabase(news);
                    }
                });
                feedToShow.add(news);
                thread.start();
            } else break;
        }
        return feedToShow;
    }

    public ArrayList<News> getFeedToShow() {
        return feed;
    }
}
