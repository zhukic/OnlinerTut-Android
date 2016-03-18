package rus.tutby.mvp.feed.presenter;

/**
 * Created by RUS on 11.03.2016.
 */
public interface FeedPresenter {

    void parse(boolean hasInternet);

    void upload();

    void onDestroy();

    void onNewsClicked(int position);
}
