package rus.tutby.utils

import android.util.Log

/**
 * Created by RUS on 04.07.2016.
 */
class Logger {

    companion object {

        val TAG: String = "TAG"

        fun log(message: String) {
            Log.d(TAG, message)
        }
    }

}