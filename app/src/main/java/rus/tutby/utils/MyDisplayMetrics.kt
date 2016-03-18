package rus.tutby.utils;


import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

class MyDisplayMetrics() {

    companion object {
        fun getWidth(windowManager: WindowManager): Float {
            val displayMetrics = DisplayMetrics();
            windowManager.defaultDisplay.getMetrics(displayMetrics);
            return displayMetrics.widthPixels / displayMetrics.density;
        }

        fun getHeight(windowManager: WindowManager): Float {
            val displayMetrics = DisplayMetrics();
            windowManager.defaultDisplay.getMetrics(displayMetrics);
            return displayMetrics.heightPixels / displayMetrics.density;
        }

        fun getWidthInPixels(windowManager: WindowManager): Int {
            val displayMetrics = DisplayMetrics();
            windowManager.defaultDisplay.getMetrics(displayMetrics);
            return displayMetrics.widthPixels;
        }

        fun getHeightInPixels(windowManager: WindowManager): Int {
            val displayMetrics = DisplayMetrics();
            windowManager.defaultDisplay.getMetrics(displayMetrics);
            return displayMetrics.heightPixels;
        }
    }
    
}