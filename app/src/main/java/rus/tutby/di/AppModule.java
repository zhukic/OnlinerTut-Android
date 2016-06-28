package rus.tutby.di;

import android.content.Context;
import android.util.Log;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rus.tutby.App;
import rus.tutby.entity.Provider;
import rus.tutby.parser.rssparser.RssParser;
import rus.tutby.presenter.NewsPresenterImpl;
import rus.tutby.repository.NewsRepository;
import rus.tutby.MainActivity;
import rus.tutby.repository.datasource.CloudDataStore;
import rus.tutby.repository.datasource.DatabaseDataStore;

/**
 * Created by RUS on 05.06.2016.
 */
@Module(library = true, injects = {CloudDataStore.class, DatabaseDataStore.class, RssParser.class, NewsPresenterImpl.class, MainActivity.class})
public class AppModule {

    private App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public Context provideApplicationContext() {
        return app;
    }

    @Provides
    public Provider getProvider() {
        Log.d("TAG", app.getProvider().toString());
        return app.getProvider(); }

}
