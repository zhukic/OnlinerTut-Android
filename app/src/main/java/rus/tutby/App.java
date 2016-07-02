package rus.tutby;

import android.app.Application;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.leakcanary.LeakCanary;

import dagger.ObjectGraph;
import rus.tutby.database.DatabaseHelper;
import rus.tutby.database.DatabaseManager;
import rus.tutby.di.AppModule;
import rus.tutby.entity.News;
import rus.tutby.entity.Provider;

public class App extends Application {

    private static DatabaseHelper databaseHelper;

    private static Dao<News, Integer> newsDao;

    private static ImageLoader imageLoader;

    private static DisplayImageOptions displayImageOptions;

    private Provider provider;

    public static ObjectGraph objectGraph;

    public static int counter;

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);

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

        provider = Provider.TUT;

        DatabaseManager.clearTable();
        objectGraph = ObjectGraph.create(new AppModule(this));

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

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Provider getProvider() {
        return provider;
    }

    public static int getCounter() {
        return counter++;
    }
}