package rus.tutby.mvp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rus.tutby.activity.NewsActivity;
import rus.tutby.adapters.NewsAdapter;
import rus.tutby.R;
import rus.tutby.mvp.presenter.FeedPresenter;
import rus.tutby.mvp.presenter.FeedPresenterImpl;
import rus.tutby.mvp.model.News;

public class FeedFragment extends Fragment implements FeedView,
        AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "TAG";

    @Bind(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.listView) ListView listView;
    @Bind(R.id.lowProgressBar) ProgressBar lowProgressBar;

    private NewsAdapter newsAdapter;
    private FeedPresenter feedPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.feed_fragment, container, false);
        ButterKnife.bind(this, rootView);

        initListView();

        swipeRefreshLayout.setOnRefreshListener(this);
        listView.setOnItemClickListener(this);

        listView.setOnScrollListener(new EndlessScroll() {
            @Override
            public void onUpload() {
                feedPresenter.upload();
            }
        });

        newsAdapter = new NewsAdapter(getActivity().getApplicationContext(), null);

        feedPresenter = new FeedPresenterImpl(
                this,
                getArguments().getString("URL"),
                getArguments().getString("CATEGORY"));

        return rootView;
    }

    private void initListView() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = (News)parent.getItemAtPosition(position);
                Intent intent = new Intent(getActivity().getApplicationContext(), NewsActivity.class);
                intent.putExtra("ID", news.getId());
                startActivity(intent);
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
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
                    Log.d(TAG, "scroll");
                    //UploadFeedTask uploadFeedTask = new UploadFeedTask();
                    //uploadFeedTask.execute();
                    loading = true;
                    //readRssTask.execute();
                }
            }
        });
    }

    @Override
    public void showRefresh() {

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void hideRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLowProgress() {
        lowProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLowProgress() {
        lowProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void setItems(List<News> items) {
        listView.setAdapter(new NewsAdapter(getActivity().getApplicationContext(), items));
    }

    @Override
    public void notifyAdapter() {
        newsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String message) {
        Snackbar snackbar = Snackbar.make(listView, "No internet connection!",
                Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        feedPresenter.parse();
                    }
                });
        if(isAdded()) {
            snackbar.show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        News news = feedPresenter.getNewsAtPosition(position);
        Intent intent = new Intent(getActivity().getApplicationContext(), NewsActivity.class);
        intent.putExtra("ID", news.getId());
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        feedPresenter.parse();
    }
}
