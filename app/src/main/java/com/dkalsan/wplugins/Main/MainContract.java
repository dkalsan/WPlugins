package com.dkalsan.wplugins.Main;

import android.support.v7.widget.RecyclerView;

public interface MainContract {
    interface View {
        void showDialog();
        void initRecyclerView();
        RecyclerView getRecyclerView();
        void showProgressBar();
        void hideProgressBar();
        void showErrorToast();
    }

    interface Presenter {
        void updatePluginsPerPagePreference(Integer value);
        void initApplication();
    }
}
