package rus.tutby.ui;

import java.util.List;

import rus.tutby.entity.News;

/**
 * Created by RUS on 11.03.2016.
 */
public interface FeedView {

    void showRefresh();

    void hideRefresh();

    void setFeed(List<News> feed);

    void notifyAdapter();

    void onError(String message);

    void openNewsActivity(int newsID);

}
