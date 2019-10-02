package geekbrains.ru.lesson5_sugarorm.sugar;

import android.os.Bundle;

import java.util.Date;
import java.util.List;

import geekbrains.ru.lesson5_sugarorm.retrofit.RetrofitModel;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SugarHelper {

    public SugarHelper() {
    }


    public Single<Bundle> save(List<RetrofitModel> modelList) {
        return Single.create((SingleOnSubscribe<Bundle>) emitter -> {

            try {
                String curLogin = "";
                String curUserID = "";
                String curAvatarUrl = "";
                Date first = new Date();
                for (RetrofitModel curItem : modelList) {
                    curLogin = curItem.getLogin();
                    curUserID = curItem.getId();
                    curAvatarUrl = curItem.getAvatarUrl();
                    SugarModel sugarModel = new SugarModel(curLogin, curUserID, curAvatarUrl);
                    sugarModel.save();
                }
                Date second = new Date();
                List<SugarModel> tempList = SugarModel.listAll(SugarModel.class);
                Bundle bundle = new Bundle();
                bundle.putInt("count", tempList.size());
                bundle.putLong("msek", second.getTime() - first.getTime());
                emitter.onSuccess(bundle);
            } catch (Exception e) {
                emitter.onError(e);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    ///
    public Single<Bundle> getAllSugar() {
        return Single.create((SingleOnSubscribe<Bundle>) emitter -> {
            try {
                Date first = new Date();
                List<SugarModel> tempList = SugarModel.listAll(SugarModel.class);
                Date second = new Date();
                Bundle bundle = new Bundle();
                bundle.putInt("count", tempList.size());
                bundle.putLong("msek", second.getTime() - first.getTime());
                emitter.onSuccess(bundle);
            } catch (Exception e) {
                emitter.onError(e);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Bundle> deleteAllSugar() {

        return Single.create((SingleOnSubscribe<Bundle>) emitter -> {
            try {
                List<SugarModel> tempList = SugarModel.listAll(SugarModel.class);
                Date first = new Date();
                SugarModel.deleteAll(SugarModel.class);
                Date second = new Date();
                Bundle bundle = new Bundle();
                bundle.putInt("count", tempList.size());
                bundle.putLong("msek", second.getTime() - first.getTime());
                emitter.onSuccess(bundle);
            } catch (Exception e) {
                emitter.onError(e);
            }

        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
