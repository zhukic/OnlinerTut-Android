package rus.tutby.di

import dagger.Module
import dagger.Provides
import rus.tutby.entity.Provider

/**
 * Created by RUS on 06.07.2016.
 */
@Module
class ProviderModule {

    @Provides
    fun getProvider(): Provider = Provider.TUT

}