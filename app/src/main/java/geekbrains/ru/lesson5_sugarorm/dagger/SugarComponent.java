package geekbrains.ru.lesson5_sugarorm.dagger;

import dagger.Subcomponent;
import geekbrains.ru.lesson5_sugarorm.sugar.SugarHelper;

@RetrofitScope
@Subcomponent(modules = {DaggerSugarModel.class})
public interface SugarComponent {
    SugarHelper getSugarHelper();


}

