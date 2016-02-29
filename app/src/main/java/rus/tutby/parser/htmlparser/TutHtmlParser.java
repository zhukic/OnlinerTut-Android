package rus.tutby.parser.htmlparser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TutHtmlParser extends HtmlParser {

    public TutHtmlParser(String url) {
        super(url);
    }

    @Override
    protected String parseHTML(Document document) {
        Elements elements = document.select("div#article_body").select("[width]");
        for(Element element : elements) {
            if(isInteger(element.attr("width")) && isInteger(element.attr("height"))) {
                int width = Integer.parseInt(element.attr("width"));
                int height = Integer.parseInt(element.attr("height"));
                int newHeight = height * 344 / width;
                element.attr("height", newHeight + "");
                element.attr("width", "100%");
            }
        }
        Element element = document.select("div#article_body").first();
        element.getElementsByClass("image-captioned").remove();
        element.getElementsByClass("image-right").remove();
        return document.select("div#article_body").html();
    }
}
