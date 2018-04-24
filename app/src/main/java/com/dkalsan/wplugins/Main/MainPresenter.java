package com.dkalsan.wplugins.Main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.dkalsan.wplugins.R;

public class MainPresenter implements MainContract.Presenter, NumberPicker.OnValueChangeListener {
    private MainContract.View mainView;
    private SharedPrefsHelper sharedPrefsHelper;
    private PluginDownloader pluginDownloader;
    private ImageDownloader imageDownloader;

    public MainPresenter(MainContract.View mainView, SharedPrefsHelper sharedPrefsHelper, PluginDownloader pluginDownloader, ImageDownloader imageDownloader) {
        this.mainView = mainView;
        this.sharedPrefsHelper = sharedPrefsHelper;
        this.pluginDownloader = pluginDownloader;
        this.imageDownloader = imageDownloader;
    }

    public void setPluginDownloadLimit(Context context, FragmentManager fragmentManager) {
        if(sharedPrefsHelper.get("firstTime", true)) {
            sharedPrefsHelper.put("firstTime", false);
            LimitPickerDialog limitPickerDialog = new LimitPickerDialog();
            limitPickerDialog.setValueChangeListener(this);
            limitPickerDialog.show(fragmentManager, "limitPicker");
        } else {
            fetchPlugins();
        }
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
        sharedPrefsHelper.put("limit", i1);
        fetchPlugins();
    }

    public void check() {
        Log.i("limit", String.valueOf(sharedPrefsHelper.get("limit", 20)));
    }

    private void fetchPlugins() {
        Log.i("NOTE", "Fetching " + sharedPrefsHelper.get("limit", 20) + " plugins.");
    }
}
