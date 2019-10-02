package geekbrains.ru.lesson5_sugarorm.sugar;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import geekbrains.ru.lesson5_sugarorm.MainActivity;

public class ConectivityModelHelper {

    public ConectivityModelHelper() {
    }


    public boolean checkConnection(MainActivity mainActivity) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = connectivityManager.getActiveNetworkInfo();

        if (networkinfo == null || !networkinfo.isConnected()) {
            // Toast.makeText(this, "Подключите интернет", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}
