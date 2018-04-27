package com.dkalsan.wplugins.Main;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.NumberPicker;

import com.dkalsan.wplugins.Main.PluginDownloaderAPI.CustomTypeAdapterFactory;
import com.dkalsan.wplugins.Main.PluginDownloaderAPI.Model.Plugin;
import com.dkalsan.wplugins.Main.PluginDownloaderAPI.Model.PluginsApiResponse;
import com.dkalsan.wplugins.Main.PluginDownloaderAPI.PluginApiService;
import com.dkalsan.wplugins.Main.PluginDownloaderAPI.PluginDownloader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View mainView;
    private SharedPrefsHelper sharedPrefsHelper;
    private PluginDownloader pluginDownloader;
    private ImageDownloader imageDownloader;
    private FragmentManager fragmentManager;

    public MainPresenter(MainContract.View mainView,
                         SharedPrefsHelper sharedPrefsHelper,
                         FragmentManager fragmentManager) {
        this.mainView = mainView;
        this.sharedPrefsHelper = sharedPrefsHelper;
        this.pluginDownloader = new PluginDownloader();
        this.imageDownloader = new ImageDownloader();
        this.fragmentManager = fragmentManager;
    }

    public void initApplication() {
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
        pluginDownloader.download(sharedPrefsHelper.get("limit", 20), mainView.getRecyclerView());
    }
}
