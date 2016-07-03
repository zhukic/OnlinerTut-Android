package rus.tutby.presenter

import android.graphics.Bitmap
import android.view.View
import com.nostra13.universalimageloader.core.assist.FailReason
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener
import org.jsoup.nodes.Document
import rus.tutby.App
import rus.tutby.entity.News
import rus.tutby.entity.NewsInfo
import rus.tutby.ui.NewsView
import rus.tutby.parser.htmlparser.HtmlParser
import rus.tutby.parser.htmlparser.OnlinerHtmlParser
import rus.tutby.parser.htmlparser.TutHtmlParser
import rus.tutby.entity.Provider
import rus.tutby.interactors.GetNewsUseCase
import rx.Subscriber
import java.sql.SQLException
import javax.inject.Inject

/**
 * Created by RUS on 17.03.2016.
 */
class NewsPresenterImpl(var newsView: NewsView?, var newsId: Int) : NewsPresenter {

    private var getNewsUseCase: GetNewsUseCase = GetNewsUseCase();

    override fun parse() {
        newsView?.showProgressDialog()
        getNewsUseCase.getNews(NewsSubscriber(), newsId)

    }

    override fun onDestroy() {
        this.getNewsUseCase.unsubscribe()
        this.newsView = null;
    }

    inner class NewsSubscriber: Subscriber<NewsInfo>() {
        override fun onCompleted() {
            newsView?.hideProgressDialog()
        }

        override fun onNext(newsInfo: NewsInfo) {
            newsView?.setNewsInfo(newsInfo)
        }

        override fun onError(e: Throwable) {
            newsView?.onError(e)
            newsView?.hideProgressDialog()
        }
    }

}