package rus.tutby;

import android.app.Application;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import rus.tutby.database.DatabaseHelper;
import rus.tutby.database.DatabaseManager;
import rus.tutby.mvp.model.News;
import rus.tutby.provider.Provider;

public class MyApplication extends Application {

    private static DatabaseHelper databaseHelper;

    private static Dao<News, Integer> newsDao;

    private static ImageLoader imageLoader;

    private static DisplayImageOptions displayImageOptions;

    private static Provider provider;

    @Override
    public void onCreate() {
        super.onCreate();

        databaseHelper = getDatabaseHelper();
        newsDao = databaseHelper.getNewsDAO();

        imageLoader = ImageLoader.getInstance();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .threadPoolSize(6)
                .threadPriority(4)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(20 * 1024 * 1024))
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .build();


        displayImageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(android.R.color.white)
                .showImageForEmptyUri(android.R.color.white)
                .showImageOnFail(android.R.color.white)
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .considerExifParams(true)
                .build();

        imageLoader.init(config);

        //DatabaseManager.clearTable();

        provider = Provider.TUT;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

    private DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    public static Dao<News, Integer> getNewsDao() {
        return newsDao;
    }

    public static ImageLoader getImageLoader() {
        return imageLoader;
    }

    public static DisplayImageOptions getDisplayImageOptions() {
        return displayImageOptions;
    }

    public static void setProvider(Provider provider) {
        MyApplication.provider = provider;
    }

    public static Provider getProvider() {
        return provider;
    }
}
