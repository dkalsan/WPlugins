package com.dkalsan.wplugins.Detail;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dkalsan.wplugins.R;

public class DetailActivity extends AppCompatActivity implements DetailContract.View, View.OnClickListener{
    private DetailContract.Presenter presenter;
    private String homepageUrl;
    private String zipUrl;
    private String pluginName;
    private final int WRITE_PERMISSION_REQUEST_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        homepageUrl = intent.getStringExtra("homepageUrl");
        zipUrl = intent.getStringExtra("zipUrl");
        pluginName = intent.getStringExtra("fileName");

        presenter = new DetailPresenter(this, this);

        Button homepageButton = findViewById(R.id.homepageButton);
        if(homepageUrl != null) {
            homepageButton.setOnClickListener(this);
        } else {
            homepageButton.setVisibility(View.GONE);
        }

        if(requestStoragePermissions())
            presenter.downloadZip(zipUrl, pluginName);
        else
            Toast.makeText(this, "Cannot download the file without permissions.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(homepageUrl));
        startActivity(intent);
    }

    private boolean requestStoragePermissions() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            return true;
        if(hasWritePermission())
            return true;

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION_REQUEST_CODE);
        return hasWritePermission();
    }

    private boolean hasWritePermission() {
        return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            presenter.downloadZip(zipUrl, pluginName);
        } else {
            Toast.makeText(this, "Cannot download the file without permissions.", Toast.LENGTH_SHORT).show();
        }
    }
}
