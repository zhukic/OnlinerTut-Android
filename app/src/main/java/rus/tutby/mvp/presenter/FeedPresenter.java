package rus.tutby.mvp.presenter;

import rus.tutby.mvp.model.News;

/**
 * Created by RUS on 11.03.2016.
 */
public interface FeedPresenter {

    void parse();

    void onResume();

    void upload();

    void onDestroy();

    News getNewsAtPosition(int position);
}
