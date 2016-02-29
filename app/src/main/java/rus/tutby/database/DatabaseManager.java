package rus.tutby.database;


import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import rus.tutby.MyApplication;
import rus.tutby.news.News;
import rus.tutby.provider.Provider;

public class DatabaseManager {

    public static ArrayList<News> getNewsListFromDatabase(String category, Provider provider) {

        ArrayList<News> currentNewsList = new ArrayList<>();
        try {
            QueryBuilder<News, Integer> queryBuilder = MyApplication.getNewsDao().queryBuilder();
            queryBuilder.where().eq("category", category).and().eq("provider", provider);
            currentNewsList = (ArrayList<News>)MyApplication.getNewsDao().query(queryBuilder.prepare());
            if(currentNewsList.size() > 100) {
                while (currentNewsList.size() != 100) {
                    if(!currentNewsList.get(0).isFavorite()) {
                        MyApplication.getNewsDao().deleteById(currentNewsList.get(0).getId());
                        currentNewsList.remove(0);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Collections.reverse(currentNewsList);
        return currentNewsList;
    }

    public static void addToDatabase(ArrayList<News> newNewsList) {
        Collections.reverse(newNewsList);
        try {
            for (News news : newNewsList) {
                MyApplication.getNewsDao().create(news);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean contains(News news) {
        try {
            QueryBuilder<News, Integer> queryBuilder = MyApplication.getNewsDao().queryBuilder();
            queryBuilder.where().eq("link", news.getLink()).and().eq("category", news.getCategory());
            ArrayList<News> newsToDelete = (ArrayList<News>) MyApplication.getNewsDao()
                    .query(queryBuilder.prepare());
            if(newsToDelete.size() != 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void update(News news) throws SQLException {
        MyApplication.getNewsDao().update(news);
    }

    public static ArrayList<News> getFavoriteNewsListFromDatabase() throws SQLException {
        QueryBuilder<News, Integer> queryBuilder = MyApplication.getNewsDao().queryBuilder();
        queryBuilder.where().eq("isFavorite", true);
        return (ArrayList<News>)MyApplication.getNewsDao().query(queryBuilder.prepare());
    }

    public static void remove(News news) {
        try {
            MyApplication.getNewsDao().deleteById(news.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void clearTable() {
        try {
            TableUtils.clearTable(MyApplication.getNewsDao().getConnectionSource(), News.class);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }
}
