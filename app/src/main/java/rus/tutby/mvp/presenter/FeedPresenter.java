package rus.tutby.mvp.presenter;

import java.util.List;

import rus.tutby.mvp.model.News;

/**
 * Created by RUS on 11.03.2016.
 */
public interface FeedPresenter {

    void parse(boolean hasInternet);

    void upload();

    void onDestroy();

    void onNewsClicked(int position);
}
