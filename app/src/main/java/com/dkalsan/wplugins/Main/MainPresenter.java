package com.dkalsan.wplugins.Main;

import android.app.FragmentManager;
import android.content.Context;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.dkalsan.wplugins.Main.PluginDownloaderAPI.CustomTypeAdapterFactory;
import com.dkalsan.wplugins.Main.PluginDownloaderAPI.Model.Plugin;
import com.dkalsan.wplugins.Main.PluginDownloaderAPI.Model.PluginsApiResponse;
import com.dkalsan.wplugins.Main.PluginDownloaderAPI.PluginApiService;
import com.dkalsan.wplugins.Main.PluginDownloaderAPI.PluginsAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View mainView;
    private SharedPrefsHelper sharedPrefsHelper;
    private List<Plugin> resultPlugins;
    private PluginsAdapter pluginsAdapter;
    private Context context;

    public MainPresenter(MainContract.View mainView,
                         Context context,
                         SharedPrefsHelper sharedPrefsHelper,
                         FragmentManager fragmentManager) {
        this.mainView = mainView;
        this.context = context;
        this.sharedPrefsHelper = sharedPrefsHelper;
    }

    public void initApplication() {
        mainView.showProgressBar();
        if(sharedPrefsHelper.get("firstTime", true)) {
            sharedPrefsHelper.put("firstTime", false);
            mainView.showDialog();
        } else {
            loadPlugins();
        }
    }

    public void updatePluginsPerPagePreference(Integer value) {
        sharedPrefsHelper.put("limit", value);
        loadPlugins();
    }

    private void loadPlugins() {
        CustomTypeAdapterFactory customTypeAdapterFactory = new CustomTypeAdapterFactory();
        final GsonBuilder gsonBuilder= new GsonBuilder();
        gsonBuilder.registerTypeAdapterFactory(customTypeAdapterFactory)
                .create();
        final Gson gson = gsonBuilder.create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.wordpress.org/plugins/info/1.1/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        PluginApiService pluginApiService = retrofit.create(PluginApiService.class);
        Call<PluginsApiResponse> response = pluginApiService.getPluginsResponse(String.valueOf(sharedPrefsHelper.get("limit", 20)));

        response.enqueue(new Callback<PluginsApiResponse>() {
            @Override
            public void onResponse(Call<PluginsApiResponse> call, Response<PluginsApiResponse> response) {
                resultPlugins = response.body().getPlugins();
                pluginsAdapter = new PluginsAdapter(resultPlugins, Glide.with(context));
                mainView.getRecyclerView().setAdapter(pluginsAdapter);
                mainView.hideProgressBar();
            }

            @Override
            public void onFailure(Call<PluginsApiResponse> call, Throwable t) {
                Log.i("pl", "err");
            }
        });
    }
}
