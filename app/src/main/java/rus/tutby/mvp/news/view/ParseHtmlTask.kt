package rus.tutby.mvp.news.view

import android.os.AsyncTask
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import rus.tutby.mvp.news.presenter.NewsParseListener
import rus.tutby.parser.htmlparser.HtmlParser

/**
 * Created by RUS on 17.03.2016.
 */
class ParseHtmlTask(val newsParseListener: NewsParseListener,val htmlParser: HtmlParser)
        : AsyncTask<String, Void, Document>() {


    override fun doInBackground(vararg params: String?): Document? {

        val html = htmlParser.html()

        return Jsoup.parse(html);
    }

    override fun onPostExecute(result: Document) {
        newsParseListener.onFinishedParse(result)
    }

}