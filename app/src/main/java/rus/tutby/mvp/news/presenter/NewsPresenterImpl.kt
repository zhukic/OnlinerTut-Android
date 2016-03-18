package rus.tutby.mvp.news.presenter

import android.graphics.Bitmap
import android.view.View
import com.nostra13.universalimageloader.core.assist.FailReason
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener
import org.jsoup.nodes.Document
import rus.tutby.MyApplication
import rus.tutby.mvp.model.News
import rus.tutby.mvp.news.view.NewsView
import rus.tutby.mvp.news.view.ParseHtmlTask
import rus.tutby.parser.htmlparser.HtmlParser
import rus.tutby.parser.htmlparser.OnlinerHtmlParser
import rus.tutby.parser.htmlparser.TutHtmlParser
import rus.tutby.provider.Provider
import java.sql.SQLException

/**
 * Created by RUS on 17.03.2016.
 */
class NewsPresenterImpl : NewsPresenter, NewsParseListener {

    var newsView: NewsView?

    var news: News? = null

    constructor(newsView: NewsView, id: Int) {
        this.newsView = newsView
        this.news = getNews(id);
    }

    override fun parse(hasInternet: Boolean) {

        MyApplication.getImageLoader().loadImage(news?.imageURL, MyApplication.getDisplayImageOptions(),
                object : ImageLoadingListener {
            override fun onLoadingComplete(imageUri: String?, view: View?, loadedImage: Bitmap?) {
                newsView?.setImage(loadedImage);
            }

            override fun onLoadingCancelled(imageUri: String?, view: View?) {
            }

            override fun onLoadingFailed(imageUri: String?, view: View?, failReason: FailReason?) {
            }

            override fun onLoadingStarted(imageUri: String?, view: View?) {
            }

        })

        val parseHtmlTask: ParseHtmlTask = ParseHtmlTask(this, getHtmlParser());
        parseHtmlTask.execute()
    }

    override fun onDestroy() {
        this.newsView = null;
    }

    override fun onFinishedParse(document: Document) {
        newsView?.setHtml(document.html())
    }

    override fun getTitle(): String = news?.title ?: ""

    override fun getDate(): String = news?.date ?: ""

    fun getNews(id: Int): News? {
        try {
            return MyApplication.getNewsDao().queryForId(id)
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return null
    }

    fun getHtmlParser(): HtmlParser {
        when(MyApplication.getProvider()) {
            Provider.TUT -> return TutHtmlParser(news?.link);
            Provider.ONLINER -> return OnlinerHtmlParser(news?.link)
        }
    }

}