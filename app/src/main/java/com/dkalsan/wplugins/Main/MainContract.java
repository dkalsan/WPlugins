package com.dkalsan.wplugins.Main;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;

public interface MainContract {
    interface View {
        void showDialog();
        void initRecyclerView();
        RecyclerView getRecyclerView();
    }

    interface Presenter {
        //void setPluginDownloadLimit();

        void updatePluginsPerPagePreference(Integer value);
        void initApplication();
    }
}
