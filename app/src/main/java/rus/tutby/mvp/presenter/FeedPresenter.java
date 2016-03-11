package rus.tutby.mvp.presenter;

import java.util.List;

import rus.tutby.mvp.model.News;

/**
 * Created by RUS on 11.03.2016.
 */
public interface FeedPresenter {

    void parse();

    void onParseStarted();

    void onParseFinished(List<News> list);

    void onResume();

    void upload();

    void onUploadStarted();

    void onUploadFinished();

    void onDestroy();

    News getNewsAtPosition(int position);
}
