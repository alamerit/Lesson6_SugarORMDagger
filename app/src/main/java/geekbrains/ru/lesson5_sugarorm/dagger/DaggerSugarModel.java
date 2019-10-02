package geekbrains.ru.lesson5_sugarorm.dagger;

import dagger.Module;
import dagger.Provides;
import geekbrains.ru.lesson5_sugarorm.sugar.SugarHelper;

@Module
public class DaggerSugarModel {

    @RetrofitScope
    @Provides
    SugarHelper getSugarHalper() {
        return new SugarHelper();
    }

}
