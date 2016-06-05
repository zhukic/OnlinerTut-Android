package rus.tutby.ui;

import android.util.Log;
import android.widget.AbsListView;

/**
 * Created by RUS on 11.03.2016.
 */
public abstract class EndlessScroll implements AbsListView.OnScrollListener {

    private int previousItemCount = 0;
    private boolean loading = true;

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        if(loading) {
            if(totalItemCount > previousItemCount) {
                loading = false;
                previousItemCount = totalItemCount;
            }
        }

        if(firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0 && !loading) {
            onUpload();
            loading = true;
        }
    }

    public abstract void onUpload();
}
