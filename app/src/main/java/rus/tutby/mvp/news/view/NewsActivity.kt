package rus.tutby.mvp.news.view

import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.animation.AlphaAnimation
import kotlinx.android.synthetic.main.activity_news.*
import rus.tutby.R
import rus.tutby.mvp.model.News
import rus.tutby.mvp.news.presenter.NewsPresenter
import rus.tutby.mvp.news.presenter.NewsPresenterImpl
import rus.tutby.utils.DateTimeFormatter
import rus.tutby.utils.Internet
import rus.tutby.utils.MyDisplayMetrics

/**
 * Created by RUS on 17.03.2016.
 */
class NewsActivity : AppCompatActivity(), NewsView {

    private final val TAG: String = "TAG"

    private final val TINT: Int = 0x40000000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        setSupportActionBar(my_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val newsPresenter: NewsPresenter = NewsPresenterImpl(this, intent.getIntExtra("ID", -1))

        textDate.typeface = Typeface.createFromAsset(assets, "Roboto-Medium.ttf")
        textDate.text = DateTimeFormatter.getLongFormattedDate(newsPresenter.getDate())

        collapsing_toolbar.title = newsPresenter.getTitle()

        newsPresenter.parse(Internet.hasNet(this))

    }

    override fun setImage(bitmap: Bitmap?) {
        val alphaAnimation: AlphaAnimation = AlphaAnimation(0f, 1f);
        alphaAnimation.duration = 1000
        imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap, bitmap!!.width,
                MyDisplayMetrics.Companion.getWidthInPixels(windowManager) * 2 / 3 , false));
        imageView.setColorFilter(TINT, PorterDuff.Mode.DARKEN);
        imageView.startAnimation(alphaAnimation);
    }

    override fun showProgress() {

    }

    override fun hideProgress() {

    }

    override fun setHtml(html: String) {
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

}
