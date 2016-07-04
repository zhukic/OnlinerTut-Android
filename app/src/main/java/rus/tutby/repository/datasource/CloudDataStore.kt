package rus.tutby.repository.datasource;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;

import rus.tutby.App;
import rus.tutby.entity.News;
import rus.tutby.parser.rssparser.RssParser;
import rus.tutby.utils.edit
import rx.Observable;
import rx.Subscriber;
import rus.tutby.utils.getSharedPreferences
import rus.tutby.utils.set
import rx.lang.kotlin.subscriber

/**
 * Created by RUS on 20.06.2016.
 */
class CloudDataStore : INewsStore {

    @Inject
    lateinit var context: Context

    init {
        App.objectGraph.inject(this)
    }

    override fun userEntityList(url: String): Observable<News> = Observable.create(object : Observable.OnSubscribe<News> {
        override fun call(subscriber: Subscriber<in News>) {
            val rssParser = RssParser(url)
            if(!rssParser.lastBuildDate.equals(getBuildDateFromPreferences(url))) {
                changeBuildDate(url, rssParser.lastBuildDate)
                for(i in 0..rssParser.size()) {
                    subscriber.onNext(rssParser.getItem(i))
                }
                subscriber.onCompleted()
            }
        }
    })

    private fun getBuildDateFromPreferences(urlKey: String) =  context.getSharedPreferences(PREFEREFENCES_NAME).getString(urlKey, "132")

    private fun changeBuildDate(urlKey: String, value: String) {
        context.getSharedPreferences(PREFEREFENCES_NAME).edit {
            set(urlKey to value)
        }
    }


    companion object {
        val PREFEREFENCES_NAME: String = "FeedBuildDatePreferences"
    }
}
