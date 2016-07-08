package rus.tutby.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rus.tutby.App;
import rus.tutby.database.DatabaseManager;
import rus.tutby.ui.adapters.NewsAdapter;
import rus.tutby.R;
import rus.tutby.presenter.FeedPresenter;
import rus.tutby.presenter.FeedPresenterImpl;
import rus.tutby.entity.News;
import rus.tutby.utils.Logger;

public class FeedFragment extends Fragment implements FeedView,
        NewsAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.recyclerView) RecyclerView recyclerView;
    @Bind(R.id.lowProgressBar) ProgressBar lowProgressBar;

    private NewsAdapter newsAdapter;
    private FeedPresenter feedPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.feed_fragment, container, false);
        ButterKnife.bind(this, rootView);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView.setLayoutManager(getLayoutManager());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        final String url = getArguments().getString("URL");
        final String category = getArguments().getString("CATEGORY");

        feedPresenter = new FeedPresenterImpl(this, url, category);

        return rootView;
    }

    private RecyclerView.LayoutManager getLayoutManager() {
        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            return new LinearLayoutManager(getActivity());
        } else return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    }

    @Override
    public void onActivityCreated(Bundle savedInstance) {
        super.onActivityCreated(savedInstance);
        feedPresenter.parse();
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
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
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
    public void setFeed(List<News> items) {
        newsAdapter = new NewsAdapter(items);
        newsAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(newsAdapter);
    }

    @Override
    public void notifyAdapter() {
        newsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String message) {
        if(isAdded()) {
            Snackbar snackbar = Snackbar.make(recyclerView, "",
                    Snackbar.LENGTH_LONG)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onRefresh();
                        }
                    });
            snackbar.setActionTextColor(Color.RED);
            snackbar.setText(message);

            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);

            snackbar.show();
        }
    }

    @Override
    public void openNewsActivity(int newsId) {
        Intent intent = new Intent(getActivity().getApplicationContext(), NewsActivity.class);
        intent.putExtra("ID", newsId);
        startActivity(intent);
    }

    @Override
    public void onItemClicked(News news, int position) {
        DatabaseManager.addToDatabase(news);
        this.openNewsActivity(news.getId());
        //feedPresenter.onNewsClicked(position);
    }

    @Override
    public void onRefresh() {
        feedPresenter.parse();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        feedPresenter.onDestroy();
    }
}
