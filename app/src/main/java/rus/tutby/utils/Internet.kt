package rus.tutby.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.j256.ormlite.android.apptools.OpenHelperManager
import rus.tutby.database.DatabaseHelper

class Internet {

    companion object {
        fun hasNet(context: Context): Boolean {
            val connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager;
            val activeNetworkInfo = connectivityManager.getActiveNetworkInfo()
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
    }
}
