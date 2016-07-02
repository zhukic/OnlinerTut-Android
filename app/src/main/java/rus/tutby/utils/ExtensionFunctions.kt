package rus.tutby.utils

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.support.design.widget.TabLayout
import android.widget.Toast

/**
 * Created by RUS on 08.06.2016.
 */
fun Context.showToast(message: String?): Boolean {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    return false;
}

fun Context.hasInternet(): Boolean {
    val connectivityManager: ConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager;
    val activeNetworkInfo = connectivityManager.getActiveNetworkInfo()
    return activeNetworkInfo != null && activeNetworkInfo.isConnected;
}

fun Context.getSharedPreferences(): SharedPreferences {
    return getSharedPreferences();
}