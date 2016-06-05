package rus.tutby.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rus.tutby.mvp.news.view.NewsActivity;
import rus.tutby.ui.adapters.NewsAdapter;
import rus.tutby.R;
import rus.tutby.presenter.FeedPresenter;
import rus.tutby.presenter.FeedPresenterImpl;
import rus.tutby.entity.News;
import rus.tutby.utils.Internet;

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

        swipeRefreshLayout.setOnRefreshListener(this);
        listView.setOnItemClickListener(this);

        listView.setOnScrollListener(new EndlessScroll() {
            @Override
            public void onUpload() {
                feedPresenter.upload();
            }
        });

        newsAdapter = new NewsAdapter(getActivity().getApplicationContext(), null);

        final String url = getArguments().getString("URL");
        final String category = getArguments().getString("CATEGORY");

        feedPresenter = new FeedPresenterImpl(this, url, category);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstance) {
        super.onActivityCreated(savedInstance);

        feedPresenter.parse(Internet.Companion.hasNet(getContext()));
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
        listView.setAdapter(new NewsAdapter(getActivity().getApplicationContext(), items));
    }

    @Override
    public void notifyAdapter() {
        newsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String message) {
        if(isAdded()) {
            Snackbar snackbar = Snackbar.make(listView, "",
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
    public void openNews(int newsID) {
        Intent intent = new Intent(getActivity().getApplicationContext(), NewsActivity.class);
        intent.putExtra("ID", newsID);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        feedPresenter.onNewsClicked(position);
    }

    @Override
    public void onRefresh() {
        feedPresenter.parse(Internet.Companion.hasNet(getContext()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        feedPresenter.onDestroy();
    }
}
