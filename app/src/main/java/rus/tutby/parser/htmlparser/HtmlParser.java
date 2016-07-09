package rus.tutby.parser.htmlparser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import javax.inject.Inject;

import rus.tutby.entity.Provider;

public abstract class HtmlParser {

    protected static final String TAG = "TAG";

    private String url;

    public HtmlParser(String url) {
        this.url = url;
    }

    protected abstract String parseHTML(Document document);

    public String html() throws IOException {
        Document document = Jsoup.connect(url).get();
        return Jsoup.parse(parseHTML(document)).html();
    }

    protected boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        return true;
    }
}
