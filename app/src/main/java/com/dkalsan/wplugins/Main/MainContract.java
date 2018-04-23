package com.dkalsan.wplugins.Main;

import android.app.FragmentManager;
import android.content.Context;

public interface MainContract {
    interface View {

    }

    interface Presenter {
        void setPluginDownloadLimit(Context context, FragmentManager fragmentManager);

        void check();
    }
}
