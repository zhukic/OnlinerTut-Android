package rus.tutby.repository.datasource;

import android.content.Context;
import android.os.SystemClock

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
class CloudDataStore {

    @Inject
    lateinit var context: Context

    init {
        App.objectGraph.inject(this)
    }

    fun userEntityList(url: String): Observable<News> = Observable.create ({ subscriber ->
        val rssParser = RssParser(url)
        for(i in 0..rssParser.size() - 1) {
            subscriber.onNext(rssParser.getItem(i))
        }
        subscriber.onCompleted()

    })

    private fun getBuildDateFromPreferences(urlKey: String) =  context.getSharedPreferences(PREFERENCES_NAME).getString(urlKey, "")

    private fun changeBuildDate(urlKey: String, value: String) {
        context.getSharedPreferences(PREFERENCES_NAME).edit {
            set(urlKey to value)
        }
    }


    companion object {
        val PREFERENCES_NAME: String = "FeedBuildDatePreferences"
    }
}
