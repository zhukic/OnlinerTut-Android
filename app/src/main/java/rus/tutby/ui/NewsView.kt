package rus.tutby.ui

import android.graphics.Bitmap

/**
 * Created by RUS on 17.03.2016.
 */
interface NewsView {

    fun setImage(bitmap: Bitmap?)

    fun setDate(date: String)

    fun setTitle(title: String)

    fun showProgressDialog()

    fun hideProgressDialog()

    fun setHtml(html: String);

}