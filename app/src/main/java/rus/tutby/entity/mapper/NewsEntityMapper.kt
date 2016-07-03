package rus.tutby.entity.mapper

import android.graphics.Bitmap
import rus.tutby.entity.News
import rus.tutby.entity.NewsInfo

/**
 * Created by RUS on 02.07.2016.
 */
class NewsEntityMapper {

    companion object {
        fun transform(news: News, bitmap: Bitmap, html: String): NewsInfo {
            val newsInfo = NewsInfo()
            newsInfo.date = news.date
            newsInfo.title = news.title
            newsInfo.url = news.link
            newsInfo.imageUrl = news.imageURL
            newsInfo.imageBitmap = bitmap
            newsInfo.html = html
            return newsInfo
        }
    }

}