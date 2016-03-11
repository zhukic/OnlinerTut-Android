package rus.tutby.activity;

import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.sql.SQLException;

import butterknife.Bind;
import butterknife.ButterKnife;
import rus.tutby.MyApplication;
import rus.tutby.R;
import rus.tutby.parser.htmlparser.HtmlParser;
import rus.tutby.parser.htmlparser.OnlinerHtmlParser;
import rus.tutby.parser.htmlparser.TutHtmlParser;
import rus.tutby.utils.DateTimeFormatter;
import rus.tutby.mvp.model.News;
import rus.tutby.utils.MyDisplayMetrics;

public class NewsActivity extends AppCompatActivity {

    private static final String TAG = "TAG";

    private static final int TINT = 0x40000000;

    @Bind(R.id.imageView) ImageView imageView;
    @Bind(R.id.textDate)  TextView textDate;
    @Bind(R.id.webView)  WebView webView;
    @Bind(R.id.collapsing_toolbar) net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(R.id.my_toolbar)  Toolbar toolbar;

    private News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        ButterKnife.bind(this);

        news = getNews();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        textDate.setTypeface(Typeface.createFromAsset(getAssets(), "Roboto-Medium.ttf"));
        textDate.setText(DateTimeFormatter.getLongFormattedDate(news.getDate()));

        collapsingToolbarLayout.setTitle(news.getTitle());

        MyApplication.getImageLoader().displayImage(news.getImageURL(), imageView,
                MyApplication.getDisplayImageOptions(), new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String s, View view) {
                    }

                    @Override
                    public void onLoadingFailed(String s, View view, FailReason failReason) {
                    }

                    @Override
                    public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
                        alphaAnimation.setDuration(1000);
                        ImageView imageView = (ImageView) view;
                        imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(),
                                MyDisplayMetrics.getWidthInPixels(getWindowManager()) * 2 / 3 , false));
                        imageView.setColorFilter(TINT, PorterDuff.Mode.DARKEN);
                        imageView.startAnimation(alphaAnimation);
                    }

                    @Override
                    public void onLoadingCancelled(String s, View view) {
                    }
                });

        ParseHTMLTask parseHTMLTask = new ParseHTMLTask();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            parseHTMLTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            parseHTMLTask.execute();


    }

    private News getNews() {
        final int id = getIntent().getIntExtra("ID", -1);

        try {
            return MyApplication.getNewsDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private HtmlParser getHtmlParser() {
        switch (news.getProvider()) {
            case TUT:
                return new TutHtmlParser(news.getLink());
            case ONLINER:
                return new OnlinerHtmlParser(news.getLink());
        }
        return null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    class ParseHTMLTask extends AsyncTask<Void, Void, Document> {

        @Override
        protected Document doInBackground(Void... params) {
            HtmlParser htmlParser = getHtmlParser();
            String html = "";
            try {
                html = htmlParser.html();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Jsoup.parse(html);
        }

        @Override
        protected void onPostExecute(Document result) {
            super.onPostExecute(result);

            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadDataWithBaseURL("", result.html(), "text/html", "UTF-8", "");
        }
    }
}
