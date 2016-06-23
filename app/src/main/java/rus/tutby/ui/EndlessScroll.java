package rus.tutby.ui;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.AbsListView;

/**
 * Created by RUS on 11.03.2016.
 */
public abstract class EndlessScroll extends RecyclerView.OnScrollListener {

    private int previousItemCount = 0;
    private boolean loading = true;

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        Log.d("TAG", newState + "");
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        //Log.d("TAG", "dx = " + dx + " dy = " + dy);
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }


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
