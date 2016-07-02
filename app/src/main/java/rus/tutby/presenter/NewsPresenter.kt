package rus.tutby.presenter

import rus.tutby.entity.News

/**
 * Created by RUS on 17.03.2016.
 */
interface NewsPresenter {

    fun parse()

    fun onDestroy()
}