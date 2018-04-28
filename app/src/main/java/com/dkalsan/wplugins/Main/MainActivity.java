package com.dkalsan.wplugins.Main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dkalsan.wplugins.R;

public class MainActivity extends AppCompatActivity implements MainContract.View, NumberPicker.OnValueChangeListener {
    private MainContract.Presenter presenter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this,
                this,
                new SharedPrefsHelper(getSharedPreferences("prefs", MODE_PRIVATE)));

        progressBar = findViewById(R.id.progressBar);
        initRecyclerView();
        presenter.initApplication();
    }

    public void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
        presenter.updatePluginsPerPagePreference(i1);
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void showDialog() {
        LimitPickerDialog limitPickerDialog = new LimitPickerDialog();
        limitPickerDialog.setValueChangeListener(this);
        limitPickerDialog.show(getFragmentManager(), "limitPicker");
    }


    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    public void showErrorToast() {
        Toast.makeText(this, "Error occured while fetching data from Wordpress API", Toast.LENGTH_SHORT).show();
    }
}
