package rus.tutby.parser.htmlparser;

import android.util.Log;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class OnlinerHtmlParser extends HtmlParser {

    public OnlinerHtmlParser(String url) {
        super(url);
    }

    @Override
    protected String parseHTML(Document document) {
        String html;
        Elements elements = document.select("div.b-posts-1-item__text").select("[width]");
        for(Element element : elements) {
            if(isInteger(element.attr("width")) && isInteger(element.attr("height"))) {
                int width = Integer.parseInt(element.attr("width"));
                int height = Integer.parseInt(element.attr("height"));
                int newHeight = height * 344 / width;
                element.attr("height", newHeight + "");
                element.attr("width", "100%");
            }
            if(!isInteger(element.attr("width"))) {
                element.attr("width", "344");
            }
        }
        html = document.select("div.b-posts-1-item b-content-posts-1-item news_for_copy")
                .html() + "\n";
        html += document.select("div.b-posts-1-item__text").html();
        return html;
    }
}
