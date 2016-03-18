package rus.tutby.mvp.news.presenter

import rus.tutby.mvp.model.News

/**
 * Created by RUS on 17.03.2016.
 */
interface NewsPresenter {

    fun parse(hasInternet: Boolean)

    fun getDate(): String

    fun getTitle(): String

    fun onDestroy()
}