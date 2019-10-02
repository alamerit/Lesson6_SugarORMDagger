package geekbrains.ru.lesson5_sugarorm;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.orm.SugarContext;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import geekbrains.ru.lesson5_sugarorm.dagger.AppComponent;
import geekbrains.ru.lesson5_sugarorm.retrofit.RetrofitModel;
import geekbrains.ru.lesson5_sugarorm.room.RoomHelper;
import geekbrains.ru.lesson5_sugarorm.sugar.ConectivityModelHelper;
import geekbrains.ru.lesson5_sugarorm.sugar.SugarHelper;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnLoad;
    //аннотацией Inject мы говорим, что объект request необходимо заполнить из компонента
    @Inject
    Single<List<RetrofitModel>> request;
    RoomHelper roomHelper;
    ConectivityModelHelper duggerConectivityModel;
    //  @Inject
    //  SugarHelper sugarHelper;
    SugarHelper sugarHelper;
    List<RetrofitModel> modelList = new ArrayList<>();
    private TextView mInfoTextView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  OrmApp.getComponent().inject(this);
        AppComponent appComponent = OrmApp.getComponent();
        duggerConectivityModel = appComponent.getConect().getConect();
        appComponent.injectsToMainActivity(this);//вызывая этот метод мы инициализируем все Inject, которые данный компонент может
        roomHelper = appComponent.roomComponent().getRoomHelper();
        sugarHelper = appComponent.sugarComponent().getSugarHelper();
        setContentView(R.layout.activity_main);
        mInfoTextView = (TextView) findViewById(R.id.tvLoad);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnLoad = (Button) findViewById(R.id.btnLoad);

        (findViewById(R.id.btnSaveAllSugar)).setOnClickListener(
                (View v) -> sugarHelper.save(modelList).subscribeWith(CreateObserver()));

        (findViewById(R.id.btnSelectAllSugar)).setOnClickListener(
                (View v) -> sugarHelper.getAllSugar().subscribeWith(CreateObserver()));

        (findViewById(R.id.btnDeleteAllSugar)).setOnClickListener(
                (View v) -> sugarHelper.deleteAllSugar().subscribeWith(CreateObserver()));


        (findViewById(R.id.btnSaveAllRoom)).setOnClickListener(
                (View v) -> roomHelper.saveAll(modelList).subscribeWith(CreateObserver()));
        (findViewById(R.id.btnSelectAllRoom)).setOnClickListener(
                (View v) -> roomHelper.getAll().subscribeWith(CreateObserver()));
        (findViewById(R.id.btnDeleteAllRoom)).setOnClickListener(
                (View view) -> roomHelper.deleteAll().subscribeWith(CreateObserver()));
        btnLoad.setOnClickListener(this);


        SugarContext.init(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SugarContext.terminate();
    }

    private DisposableSingleObserver<Bundle> CreateObserver() {
        return new DisposableSingleObserver<Bundle>() {
            @Override
            protected void onStart() {
                super.onStart();
                progressBar.setVisibility(View.VISIBLE);
                mInfoTextView.setText("");
            }

            @Override
            public void onSuccess(@NonNull Bundle bundle) {
                progressBar.setVisibility(View.GONE);
                mInfoTextView.append("количество = " + bundle.getInt("count") +
                        "\n милисекунд = " + bundle.getLong("msek"));
            }

            @Override
            public void onError(@NonNull Throwable e) {
                progressBar.setVisibility(View.GONE);
                mInfoTextView.setText("ошибка БД: " + e.getMessage());
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLoad:
                mInfoTextView.setText("");
                // Подготовили вызов на сервер
                if (checkConnection()) return;
                // Запускаем
                progressBar.setVisibility(View.VISIBLE);
                downloadOneUrl(request);
                break;
        }
    }

    private boolean checkConnection() {
        boolean b = duggerConectivityModel.checkConnection(this);
        if (b)
            Toast.makeText(this, "Подключите интернет", Toast.LENGTH_SHORT).show();
        return b;


    }

    private void downloadOneUrl(Single<List<RetrofitModel>> call) {
        call.subscribe(new DisposableSingleObserver<List<RetrofitModel>>() {
            @Override
            protected void onStart() {
                super.onStart();
                progressBar.setVisibility(View.VISIBLE);
                mInfoTextView.setText("");
                modelList.clear();
            }

            @Override
            public void onSuccess(List<RetrofitModel> retrofitModels) {
                mInfoTextView.append("\n Size = " + retrofitModels.size() +
                        "\n-----------------");
                for (RetrofitModel curModel : retrofitModels) {
                    modelList.add(curModel);
                    mInfoTextView.append(
                            "\nLogin = " + curModel.getLogin() +
                                    "\nId = " + curModel.getId() +
                                    "\nURI = " + curModel.getAvatarUrl() +
                                    "\n-----------------");
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                mInfoTextView.setText("onFailure " + e.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
