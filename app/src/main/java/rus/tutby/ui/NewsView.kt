package rus.tutby.ui

import android.graphics.Bitmap
import rus.tutby.entity.NewsInfo

/**
 * Created by RUS on 17.03.2016.
 */
interface NewsView {

    fun setNewsInfo(newsInfo: NewsInfo)

    fun showProgressDialog()

    fun hideProgressDialog()

    fun onError(e: Throwable?)

}