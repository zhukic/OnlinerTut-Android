package rus.tutby.utils;


import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

public class MyDisplayMetrics {

    public static float getWidth(WindowManager windowManager) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels / displayMetrics.density;
    }

    public static float getHeight(WindowManager windowManager) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels / displayMetrics.density;
    }

    public static int getWidthInPixels(WindowManager windowManager) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.widthPixels;
    }

    public static int getHeightInPixels(WindowManager windowManager) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.heightPixels;
    }
}
