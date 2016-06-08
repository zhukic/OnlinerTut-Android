package rus.tutby.utils

import android.content.Context
import android.support.design.widget.TabLayout
import android.widget.Toast

/**
 * Created by RUS on 08.06.2016.
 */
fun Context.showToast(message: String): Boolean {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    return false;
}