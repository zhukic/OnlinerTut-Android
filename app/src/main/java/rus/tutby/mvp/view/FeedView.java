package rus.tutby.mvp.view;

import java.util.List;

import rus.tutby.mvp.model.News;

/**
 * Created by RUS on 11.03.2016.
 */
public interface FeedView {

    void showRefresh();

    void hideRefresh();

    void showLowProgress();

    void hideLowProgress();

    void setItems(List<News> items);

    void notifyAdapter();

    void onError(String message);

}
