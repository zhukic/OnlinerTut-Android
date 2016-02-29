package rus.tutby.parser.rssparser;

import rus.tutby.exception.NoInternetException;
import rus.tutby.provider.Provider;

/**
 * Created by RUS on 22.02.2016.
 */
public class TutRssParser extends RssParser {

    public TutRssParser(String url, Provider provider) throws NoInternetException {
        super(url, provider);
    }
}
