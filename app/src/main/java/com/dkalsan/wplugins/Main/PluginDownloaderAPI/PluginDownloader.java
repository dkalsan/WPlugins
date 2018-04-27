package com.dkalsan.wplugins.Main.PluginDownloaderAPI;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.dkalsan.wplugins.Main.PluginDownloaderAPI.Model.Plugin;
import com.dkalsan.wplugins.Main.PluginDownloaderAPI.Model.PluginsApiResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PluginDownloader {
    private List<Plugin> resultPlugins;
    private RecyclerView recyclerView;
    private PluginsAdapter pluginsAdapter;

    public void download(Integer numberOfPlugins, RecyclerView targetRecyclerView) {
        resultPlugins = null;
        recyclerView = targetRecyclerView;

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
        Call<PluginsApiResponse> response = pluginApiService.getPluginsResponse(String.valueOf(numberOfPlugins));

        response.enqueue(new Callback<PluginsApiResponse>() {
            @Override
            public void onResponse(Call<PluginsApiResponse> call, Response<PluginsApiResponse> response) {
                resultPlugins = response.body().getPlugins();
                pluginsAdapter = new PluginsAdapter(resultPlugins);
                recyclerView.setAdapter(pluginsAdapter);
            }

            @Override
            public void onFailure(Call<PluginsApiResponse> call, Throwable t) {
                Log.i("pl", "err");
            }
        });

        Log.i("pl", "end");
    }
}
