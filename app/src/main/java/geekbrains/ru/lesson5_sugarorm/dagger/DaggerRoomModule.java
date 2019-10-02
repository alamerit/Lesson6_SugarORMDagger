package geekbrains.ru.lesson5_sugarorm.dagger;

import dagger.Module;
import dagger.Provides;
import geekbrains.ru.lesson5_sugarorm.room.RoomHelper;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class DaggerRoomModule {
    @RetrofitScope
    @Provides
    RoomHelper getRoomHelper() {
        return new RoomHelper();
    }


    @RetrofitScope
    @Provides
    Retrofit makeRetrofit2() {
        return new Retrofit.Builder()
                .baseUrl("https://api.github.com/") // Обратить внимание на слеш в базовом адресе
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

}
