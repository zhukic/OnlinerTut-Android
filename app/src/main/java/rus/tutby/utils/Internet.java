package rus.tutby.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Internet {

    public static boolean hasWifi(Context context) {
        try {
            String cs = Context.CONNECTIVITY_SERVICE;
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(cs);
            NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            int TRY_COUNT = 10;
            boolean value = false;
            for (int i = 0; i < TRY_COUNT; i++) {
                if (wifi != null && wifi.isConnected()) {
                    value = true;
                    break;
                }
            }
            return value;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean hasMobile(Context context) {
        try {
            String cs = Context.CONNECTIVITY_SERVICE;
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(cs);
            NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            int TRY_COUNT = 10;
            boolean value = false;
            for (int i = 0; i < TRY_COUNT; i++) {
                if (mobile != null && mobile.isConnected()) {
                    value = true;
                    break;
                }
            }
            return value;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean hasNet(Context context) {
        return hasWifi(context) || hasMobile(context);
    }

}
