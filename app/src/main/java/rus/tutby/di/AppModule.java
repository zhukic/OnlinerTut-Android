package rus.tutby.di;

import android.content.Context;
import android.util.Log;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rus.tutby.App;
import rus.tutby.entity.Provider;
import rus.tutby.interactors.GetNewsUseCase;
import rus.tutby.presenter.NewsPresenterImpl;
import rus.tutby.MainActivity;
import rus.tutby.repository.datasource.CloudDataStore;

/**
 * Created by RUS on 05.06.2016.
 */
@Module(library = true, injects = {GetNewsUseCase.class, CloudDataStore.class, NewsPresenterImpl.class, MainActivity.class})
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
