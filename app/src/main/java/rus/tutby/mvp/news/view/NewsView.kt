package rus.tutby.mvp.news.view

import android.graphics.Bitmap

/**
 * Created by RUS on 17.03.2016.
 */
interface NewsView {

    fun setImage(bitmap: Bitmap?)

    fun showProgress()

    fun hideProgress()

    fun setHtml(html: String);

}