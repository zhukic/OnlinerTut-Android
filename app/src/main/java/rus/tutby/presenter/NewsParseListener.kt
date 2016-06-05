package rus.tutby.presenter

import org.jsoup.nodes.Document

/**
 * Created by RUS on 17.03.2016.
 */
interface NewsParseListener {

    fun onFinishedParse(documet: Document)

}