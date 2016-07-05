package rus.tutby.ui

import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.os.AsyncTask
import android.os.Bundle
import android.os.SystemClock
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.animation.AlphaAnimation
import com.afollestad.materialdialogs.MaterialDialog
import kotlinx.android.synthetic.main.activity_news.*
import rus.tutby.R
import rus.tutby.entity.News
import rus.tutby.entity.NewsInfo
import rus.tutby.presenter.NewsPresenter
import rus.tutby.presenter.NewsPresenterImpl
import rus.tutby.utils.DateTimeFormatter
import rus.tutby.utils.MyDisplayMetrics
import rus.tutby.utils.hasInternet
import rus.tutby.utils.showToast

/**
 * Created by RUS on 17.03.2016.
 */
class NewsActivity : AppCompatActivity(), NewsView {

    companion object {
        private val TINT: Int = 0x40000000
    }

    lateinit var newsPresenter: NewsPresenter;

    private val progressDialog: MaterialDialog by lazy {
        MaterialDialog.Builder(this)
                .title(R.string.news_loading)
                .content(R.string.wait)
                .progress(true, 0)
                .build() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        setSupportActionBar(my_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        newsPresenter = NewsPresenterImpl(this, intent.getIntExtra("ID", -1))

        newsPresenter.parse()

    }

    override fun showProgressDialog() = progressDialog.show()

    override fun hideProgressDialog() = progressDialog.dismiss()

    override fun setNewsInfo(newsInfo: NewsInfo) {
        setImage(newsInfo.imageBitmap)
        setTitle(newsInfo.title)
        setDate(newsInfo.date)
        setHtml(newsInfo.html)
    }

    override fun onError(e: Throwable?) = showToast(e?.message)

    private fun setImage(bitmap: Bitmap?) {
        val alphaAnimation: AlphaAnimation = AlphaAnimation(0f, 1f);
        alphaAnimation.duration = 1000
        imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap, bitmap!!.width,
                MyDisplayMetrics.getWidthInPixels(windowManager) * 2 / 3 , false));
        imageView.setColorFilter(TINT, PorterDuff.Mode.DARKEN);
        imageView.startAnimation(alphaAnimation);
    }

    private fun setTitle(title: String) {
        collapsing_toolbar.title = title
    }

    private fun setDate(date: String) {
        textDate.text = date
    }

    private fun setHtml(html: String) {
        webView.settings.javaScriptEnabled = true
        webView.loadDataWithBaseURL("", html, "text/html", "UTF-8", "")
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId) {
            R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                return true
            }
        }
        return super.onOptionsItemSelected(menuItem)
    }

    override fun onDestroy() {
        super.onDestroy()
        newsPresenter.onDestroy()
    }

}
