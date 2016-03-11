package rus.tutby.mvp.presenter;

import java.util.List;

import rus.tutby.mvp.model.News;

/**
 * Created by RUS on 11.03.2016.
 */
public interface OnFinishedListener {

    public void onFinished(List<News> list);

}
