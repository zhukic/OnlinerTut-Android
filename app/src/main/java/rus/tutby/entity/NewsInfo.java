package rus.tutby.entity;

import android.graphics.Bitmap;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by RUS on 02.07.2016.
 */
public class NewsInfo {

    private String title;

    private String date;

    private String imageUrl;

    private String url;

    private Bitmap imageBitmap;

    private String html;

    public NewsInfo() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
