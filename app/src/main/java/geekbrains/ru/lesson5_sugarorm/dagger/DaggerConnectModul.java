package geekbrains.ru.lesson5_sugarorm.dagger;

import dagger.Module;
import dagger.Provides;
import geekbrains.ru.lesson5_sugarorm.sugar.ConectivityModelHelper;

@Module
public class DaggerConnectModul {

    @RetrofitScope
    @Provides
    public ConectivityModelHelper getConect() {
        return new ConectivityModelHelper();
    }

}


