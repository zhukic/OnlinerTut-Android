package rus.tutby.presenter;

/**
 * Created by RUS on 11.03.2016.
 */
public interface FeedPresenter {

    void parse();

    void onDestroy();

    void onNewsClicked(int position);
}
