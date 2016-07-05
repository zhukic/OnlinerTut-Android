package rus.tutby.repository.datasource

import android.os.SystemClock
import rus.tutby.App
import rus.tutby.entity.News
import rus.tutby.parser.rssparser.RssParser
import rx.Observable

/**
 * Created by RUS on 05.07.2016.
 */
class DbDataStore {

    fun getNewsFromDb(newsId: Int): Observable<News> = Observable.create ({ subscriber ->
        val news = App.getNewsDao().queryForId(newsId)
        subscriber.onNext(news)
        subscriber.onCompleted()
    })

}
