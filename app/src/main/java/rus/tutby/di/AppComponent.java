package rus.tutby.di;

import javax.inject.Singleton;

import dagger.Component;
import rus.tutby.ui.MainActivity;

/**
 * Created by RUS on 06.07.2016.
 */
@Component(modules = AppModule.class)
@Singleton
public interface AppComponent {

    void inject(MainActivity mainActivity);

}
