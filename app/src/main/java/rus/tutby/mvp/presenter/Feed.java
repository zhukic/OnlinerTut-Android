package rus.tutby.mvp.presenter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import rus.tutby.MyApplication;
import rus.tutby.database.DatabaseManager;
import rus.tutby.mvp.model.News;
import rus.tutby.provider.Provider;

/**
 * Created by RUS on 13.03.2016.
 */
public class Feed {

    private ArrayList<News> feed;

    private ArrayList<News> feedToShow;

    public Feed() {
        feed = new ArrayList<>();
        feedToShow = new ArrayList<>();
    }

    private News getEqualNews(News news) {
        for(News i : feed) {
            if(i.getCategory().equals(news.getCategory()) && i.getLink().equals(news.getLink()))
                return i;
        }
        return null;
    }

    public int getSize() {
        return feed.size();
    }

    public void addOrUpdate(News news) throws SQLException {
        News equalNews = getEqualNews(news);
        if(equalNews != null) {
            if(!equalNews.getDate().equals(news.getDate())) {
                DatabaseManager.update(news);
            }
        } else {
            DatabaseManager.addToDatabase(news);
            feed.add(0, news);
            if(feed.size() == 101) {
                MyApplication.getNewsDao().deleteById(feed.get(feed.size() - 1).getId());
                feed.remove(feed.size() - 1);
            }
        }
    }

    public News getNews(int position) {
        return feed.get(position);
    }

    public void setNewsList(ArrayList newsList) {
        this.feed = newsList;
    }

    public ArrayList<News> uploadFeedToShow() {
        final int size = feedToShow.size();
        for(int i = size; i < size + 20; i++) {
            if(i != feed.size()) {
                feedToShow.add(feed.get(i));
            } else break;
        }
        return feedToShow;
    }

    public ArrayList<News> getFeedToShow() {
        feedToShow = new ArrayList<>(feed.subList(0, 20));

        return feedToShow;
    }
}
