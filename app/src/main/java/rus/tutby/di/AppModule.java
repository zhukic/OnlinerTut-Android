package rus.tutby.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rus.tutby.App;

/**
 * Created by RUS on 05.06.2016.
 */
public class AppModule {

    private App app;

    public AppModule(App app) {
        this.app = app;
    }

    public Context provideApplicationContext() {
        return app;
    }

}
