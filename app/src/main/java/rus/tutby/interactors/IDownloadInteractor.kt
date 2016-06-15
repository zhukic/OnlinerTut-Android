package rus.tutby.interactors

import rus.tutby.entity.News
import rus.tutby.presenter.Feed
import rx.Observable

/**
 * Created by RUS on 15.06.2016.
 */
interface IDownloadInteractor {

    interface OnFinishedListener {

        fun onDownloadFinished(artists: List<News>)

        fun onDownloadError(t: Throwable)

    }

    fun downloadNews(onFinishedListener: OnFinishedListener): Observable<Feed>?
}