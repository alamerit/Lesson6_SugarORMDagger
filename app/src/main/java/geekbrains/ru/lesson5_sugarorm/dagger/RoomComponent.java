package geekbrains.ru.lesson5_sugarorm.dagger;

import dagger.Subcomponent;
import geekbrains.ru.lesson5_sugarorm.room.RoomHelper;


@RetrofitScope
@Subcomponent(modules = {DaggerRoomModule.class})
public interface RoomComponent {
    RoomHelper getRoomHelper();


}



