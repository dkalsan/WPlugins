package com.dkalsan.wplugins.Main;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.dkalsan.wplugins.Detail.DetailActivity;
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
                         SharedPrefsHelper sharedPrefsHelper) {
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
            public void onResponse(@NonNull Call<PluginsApiResponse> call, @NonNull Response<PluginsApiResponse> response) {
                if(response.body() != null) {
                    resultPlugins = response.body().getPlugins();

                    PluginsAdapter.OnItemClickListener recyclerViewOnItemClickListener = (view, position) -> {
                        downloadPluginZIP(resultPlugins.get(position).getHomepage(), resultPlugins.get(position).getDownloadLink(), resultPlugins.get(position).getName());
                    };

                    pluginsAdapter = new PluginsAdapter(resultPlugins, Glide.with(context));
                    pluginsAdapter.setOnItemClickListener(recyclerViewOnItemClickListener);
                    mainView.getRecyclerView().setAdapter(pluginsAdapter);
                    mainView.hideProgressBar();
                }
            }

            @Override
            public void onFailure(@NonNull Call<PluginsApiResponse> call, @NonNull Throwable t) {
                mainView.showErrorToast();
            }
        });
    }

    private void downloadPluginZIP(String homepageUrl, String zipDownloadUrl, String filename) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("homepageUrl", homepageUrl);
        intent.putExtra("zipUrl", zipDownloadUrl);
        intent.putExtra("fileName", filename);
        context.startActivity(intent);
    }
}
