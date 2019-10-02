package geekbrains.ru.lesson5_sugarorm;

import android.app.Application;

import androidx.room.Room;

import geekbrains.ru.lesson5_sugarorm.dagger.AppComponent;
import geekbrains.ru.lesson5_sugarorm.dagger.DaggerAppComponent;
import geekbrains.ru.lesson5_sugarorm.dagger.DaggerNetModule;
import geekbrains.ru.lesson5_sugarorm.dagger.RoomComponent;
import geekbrains.ru.lesson5_sugarorm.room.MyDatabase;

public class OrmApp extends Application {

    private static final String DATABASE_NAME = "DATABASE_USER_GIT";
    public static MyDatabase database;
    public static OrmApp INSTANCE;
    private static AppComponent component;
    private static RoomComponent roomComponent;

    public static OrmApp get() {
        return INSTANCE;
    }

    public static AppComponent getComponent() {
        return component;
    }

    public static RoomComponent getRoomComponent() {
        return roomComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(getApplicationContext(), MyDatabase.class, DATABASE_NAME).build();
        INSTANCE = this;
        //Dagger create
        component = DaggerAppComponent.builder().daggerNetModule(new DaggerNetModule(getApplicationContext())).build();
    }

    public MyDatabase getDB() {
        return database;
    }
}