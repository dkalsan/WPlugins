package com.dkalsan.wplugins.Main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.dkalsan.wplugins.R;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    private MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this,
                new SharedPrefsHelper(getSharedPreferences("prefs", MODE_PRIVATE)),
                new PluginDownloader(),
                new ImageDownloader()
                );

        presenter.setPluginDownloadLimit(this, getFragmentManager());
    }

    public void checkValue(View view) {
        presenter.check();
    }

}
