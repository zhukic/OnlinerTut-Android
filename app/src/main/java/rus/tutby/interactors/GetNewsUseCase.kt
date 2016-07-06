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
import rx.functions.Func2;
import rx.observers.Observers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Created by RUS on 02.07.2016.
 */
class GetNewsUseCase {

    @Inject
    lateinit var provider: Provider

    private lateinit var subscription: Subscription

    private lateinit var newsRepository: NewsRepository

    init {
        subscription = Subscriptions.empty()
        newsRepository = NewsRepository()
    }

    fun getNews(subscriber: Subscriber<NewsInfo>, newsId: Int) {
        this.subscription = newsRepository.getNewsById(newsId)
                .subscribe(NewsSubscriber(subscriber))
    }

    fun unsubscribe() = this.subscription.unsubscribe()

    private inner class NewsSubscriber(val subscriber: Subscriber<NewsInfo>) : Action1<News> {
        override fun call(news: News) {
            Observable.zip(Observable.create(BitmapObservable(news.imageURL)),
                    Observable.create(HtmlObservable(news.link)),
                    {b: Bitmap, s: String -> NewsEntityMapper.transform(news, b, s) } )
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(subscriber)
        }

    }

    private inner class BitmapObservable(val imageUrl: String) : Observable.OnSubscribe<Bitmap> {
        override fun call(subscriber: Subscriber<in Bitmap>) {
            App.getImageLoader().loadImage(imageUrl, App.getDisplayImageOptions(), object : ImageLoadingListener {
                override fun onLoadingFailed(imageUri: String?, view: View?, failReason: FailReason?) {
                }

                override fun onLoadingStarted(imageUri: String?, view: View?) {
                }

                override fun onLoadingCancelled(imageUri: String?, view: View?) {
                }

                override fun onLoadingComplete(imageUri: String?, view: View?, loadedImage: Bitmap?) {
                    subscriber.onNext(loadedImage)
                    subscriber.onCompleted()
                }
            })
        }

    }

    private inner class HtmlObservable(val url: String) : Observable.OnSubscribe<String> {
        override fun call(subscriber: Subscriber<in String>) {
            subscriber.onNext(downloadHtml(url))
            subscriber.onCompleted()
        }

        private fun downloadHtml(url: String): String = when(provider) {
            Provider.TUT -> TutHtmlParser(url).html()
            Provider.ONLINER -> OnlinerHtmlParser(url).html()
        }
    }

}
