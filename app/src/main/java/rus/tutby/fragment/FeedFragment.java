package rus.tutby.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import butterknife.Bind;
import butterknife.ButterKnife;
import rus.tutby.activity.NewsActivity;
import rus.tutby.adapters.NewsAdapter;
import rus.tutby.parser.rssparser.RssParser;
import rus.tutby.MyApplication;
import rus.tutby.R;
import rus.tutby.database.DatabaseManager;
import rus.tutby.exception.NoInternetException;
import rus.tutby.news.News;
import rus.tutby.news.FeedBuildDate;

public class FeedFragment extends Fragment {

    private static final String TAG = "TAG";

    @Bind(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.listView) ListView listView;
    @Bind(R.id.lowProgressBar) ProgressBar lowProgressBar;

    private String url;
    private String category;

    private LinkedList<News> newsList;
    private ArrayAdapter<News> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.feed_fragment, container, false);
        ButterKnife.bind(this, rootView);

        this.url = getArguments().getString("URL");
        this.category = getArguments().getString("CATEGORY");
        this.newsList = new LinkedList<>();

        initListView();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshListener());

        ReadRssTask readRssTask = new ReadRssTask();
        readRssTask.execute(url, category);

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
                    UploadFeedTask uploadFeedTask = new UploadFeedTask();
                    uploadFeedTask.execute();
                    loading = true;
                    //readRssTask.execute();
                }
            }
        });
    }

    private class UploadFeedTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            lowProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            final int size = newsList.size();

            final ArrayList<News> newsFromBase = DatabaseManager
                    .getNewsListFromDatabase(category, MyApplication.getProvider());

            for(int i = size; i < size + 20; i++) {
                if(i == newsFromBase.size()) {
                    break;
                } else {
                    newsList.add(newsFromBase.get(i));
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            adapter.notifyDataSetChanged();
            lowProgressBar.setVisibility(View.GONE);
        }
    }

    private class ReadRssTask extends AsyncTask<String, Void, Void> {

        private NoInternetException noInternetException;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(true);
                }
            });

            newsList.clear();
        }

        @Override
        protected Void doInBackground(String... params) {

            ArrayList<News> newsFromRss = new ArrayList<>();

            String url = params[0];
            String category = params[1];

            try {
                RssParser rssParser = new RssParser(url, MyApplication.getProvider());

                String lastBuildDate = rssParser.getLastBuildDate();

                if(!lastBuildDate.equals(FeedBuildDate.getBuildDate(category))) {
                    for(int i = 0; i < rssParser.size(); i++) {
                        News news = rssParser.getItem(i);
                        news.setCategory(category);
                        if(DatabaseManager.contains(news)) {
                            DatabaseManager.update(news);
                            break;
                        } else newsFromRss.add(news);
                    }
                } else FeedBuildDate.changeBuildDate(category, lastBuildDate);
                DatabaseManager.addToDatabase(newsFromRss);
            } catch (NoInternetException e) {
                noInternetException = e;
            } catch (SQLException e) {
                e.printStackTrace();
            }

            ArrayList<News> newsFromBase = DatabaseManager
                    .getNewsListFromDatabase(category, MyApplication.getProvider());

            final int size = newsList.size();

            for(int i = size; i < 20; i++) {
                if(i == newsFromBase.size()) {
                    break;
                }
                newsList.add(newsFromBase.get(i));
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            Snackbar snackbar = null;
            AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);

            swipeRefreshLayout.setRefreshing(false);

            if(noInternetException != null) {
                snackbar = Snackbar.make(listView, "No internet connection!",
                        Snackbar.LENGTH_LONG)
                        .setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ReadRssTask readRssTask = new ReadRssTask();
                                readRssTask.execute(url, category);
                            }
                        });
                snackbar.setActionTextColor(getResources().getColor(R.color.yellow));
                alphaAnimation.setDuration(0);
            } else {
                alphaAnimation.setDuration(500);
            }

            if (isAdded()) {
                if(snackbar != null) {
                    snackbar.show();
                }

                adapter = new NewsAdapter(getActivity().getApplicationContext(), newsList);
                listView.setAdapter(adapter);
                listView.setAnimation(alphaAnimation);
            }
        }
    }

    private class SwipeRefreshListener implements SwipeRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            ReadRssTask readRssTask = new ReadRssTask();
            readRssTask.execute(url, category);
        }
    }
}
