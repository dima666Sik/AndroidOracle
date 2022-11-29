package ua.gura.com.example.androidoracle.activities;

import android.app.Application;

import androidx.lifecycle.ViewModelProvider;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ua.gura.com.example.androidoracle.activities.logger.AndroidLogger;
import ua.gura.com.example.androidoracle.activities.logger.Logger;
import ua.gura.com.example.androidoracle.activities.model.DateNagerService;
import ua.gura.com.example.androidoracle.activities.model.network.DateNagerApi;

public class App extends Application {

    private ViewModelProvider.Factory factory;
    private static final String BASE_URL = "https://date.nager.at/";

    @Override
    public void onCreate() {
        super.onCreate();
        Logger logger = new AndroidLogger();
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DateNagerApi dateNagerApi = retrofit.create(DateNagerApi.class);
        DateNagerService dateNagerService = new DateNagerService(dateNagerApi, logger);
        factory = new ViewModelFactory(dateNagerService);
    }

    public ViewModelProvider.Factory getViewModelFactory() {
        return factory;
    }
}
