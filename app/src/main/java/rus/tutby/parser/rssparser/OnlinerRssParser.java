package rus.tutby.parser.rssparser;

import rus.tutby.exception.NoInternetException;
import rus.tutby.provider.Provider;


public class OnlinerRssParser extends RssParser {

    public OnlinerRssParser(String url, Provider provider) throws NoInternetException {
        super(url, provider);
    }
}
