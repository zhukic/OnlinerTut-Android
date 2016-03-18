package rus.tutby.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

class Internet {

    companion object {
        fun hasNet(context: Context): Boolean {
            val connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager;
            val activeNetworkInfo = connectivityManager.getActiveNetworkInfo()
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
    }
}
