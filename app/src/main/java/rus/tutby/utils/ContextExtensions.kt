package rus.tutby.utils

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.support.design.widget.TabLayout
import android.widget.Toast

/**
 * Created by RUS on 08.06.2016.
 */
fun Context.showToast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.hasInternet(): Boolean {
    val connectivityManager: ConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager;
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected;
}

fun Context.getSharedPreferences(name: String): SharedPreferences = getSharedPreferences(name, Context.MODE_PRIVATE)