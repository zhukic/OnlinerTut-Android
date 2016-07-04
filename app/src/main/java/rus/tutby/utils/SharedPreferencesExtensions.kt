package rus.tutby.utils

import android.content.SharedPreferences

/**
 * Created by RUS on 04.07.2016.
 */
inline fun SharedPreferences.edit(func: SharedPreferences.Editor.() -> Unit) {
    val editor = edit()
    editor.func()
    editor.apply()
}

fun SharedPreferences.Editor.set(pair: Pair<String, String>) {
    putString(pair.first, pair.second)
}
