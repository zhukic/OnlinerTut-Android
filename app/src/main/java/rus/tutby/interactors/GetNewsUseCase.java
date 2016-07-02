package rus.tutby.interactors;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.IOException;
import java.sql.SQLException;

import javax.inject.Inject;

import rus.tutby.App;
import rus.tutby.database.DatabaseHelper;
import rus.tutby.database.DatabaseManager;
import rus.tutby.entity.News;
import rus.tutby.entity.NewsInfo;
import rus.tutby.entity.Provider;
import rus.tutby.entity.mapper.NewsEntityMapper;
import rus.tutby.parser.htmlparser.HtmlParser;
import rus.tutby.parser.htmlparser.OnlinerHtmlParser;
import rus.tutby.parser.htmlparser.TutHtmlParser;
import rus.tutby.repository.NewsRepository;
import rx.Notification;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observers.Observers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Created by RUS on 02.07.2016.
 */
public class GetNewsUseCase {

    @Inject
    Provider provider;

    private Subscription subscription = Subscriptions.empty();

    private NewsRepository newsRepository;

    public GetNewsUseCase() {
        App.objectGraph.inject(this);
        newsRepository = new NewsRepository();
    }

    public void getNews(Subscriber subscriber, final int newsId) {
        this.subscription = newsRepository.getNewsById(newsId)
                .map(new Func1<News, NewsInfo>() {
                    @Override
                    public NewsInfo call(News news) {
                        return NewsEntityMapper.Companion.transform(news);
                    }
                }).map(new Func1<NewsInfo, NewsInfo>() {
                    @Override
                    public NewsInfo call(NewsInfo newsInfo) {
                        downloadImage(newsInfo);
                        return newsInfo;
                    }
                }).map(new Func1<NewsInfo, NewsInfo>() {
                    @Override
                    public NewsInfo call(NewsInfo newsInfo) {
                        try {
                            downloadHtml(newsInfo);
                            if(true)
                                throw new IOException("124");
                        } catch (IOException e) {
                           e.printStackTrace();
                        }
                        return newsInfo;
                    }
                }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    private void downloadImage(final NewsInfo newsInfo) {
        App.getImageLoader().loadImage(newsInfo.getImageUrl(), App.getDisplayImageOptions(), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
               newsInfo.setImageBitmap(loadedImage);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });

    }

    private void downloadHtml(final NewsInfo newsInfo) throws IOException {
        switch (provider) {
            case TUT:
                newsInfo.setHtml(new TutHtmlParser(newsInfo.getUrl()).html());
                break;
            case ONLINER:
                newsInfo.setHtml(new OnlinerHtmlParser(newsInfo.getUrl()).html());
                break;
        }
    }

    public void unsubscribe() {
        this.subscription.unsubscribe();
    }

}
