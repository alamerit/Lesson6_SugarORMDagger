package geekbrains.ru.lesson5_sugarorm.dagger;

import dagger.Subcomponent;
import geekbrains.ru.lesson5_sugarorm.sugar.ConectivityModelHelper;


@RetrofitScope
@Subcomponent(modules = {DaggerConnectModul.class})
public interface ConModul {
    ConectivityModelHelper getConect();


}

