package geekbrains.ru.lesson5_sugarorm.dagger;

import javax.inject.Singleton;

import dagger.Component;
import geekbrains.ru.lesson5_sugarorm.MainActivity;
import retrofit2.Retrofit;

@Singleton

@Component(modules = {DaggerNetModule.class, DaggerSugarModel.class})
public interface AppComponent {
    void injectsToMainActivity(MainActivity mainActivity);

    Retrofit getRetrofit();

    RoomComponent roomComponent();

    SugarComponent sugarComponent();

    ConModul getConect();
    // void inject (MainActivity mainActivity);


}
