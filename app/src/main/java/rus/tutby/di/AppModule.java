package rus.tutby.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rus.tutby.App;
import rus.tutby.entity.Provider;
import rus.tutby.interactors.GetNewsUseCase;
import rus.tutby.parser.rssparser.RssParser;
import rus.tutby.ui.MainActivity;
import rus.tutby.repository.datasource.CloudDataStore;

/**
 * Created by RUS on 05.06.2016.
 */
@Module
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
        return app.getProvider();
    }

}
