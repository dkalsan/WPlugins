package com.dkalsan.wplugins.Detail;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

public class DetailPresenter implements DetailContract.Presenter {
    private DetailContract.View view;
    private Context context;

    DetailPresenter(DetailContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void downloadZip(String zipUrl) {
            Uri uri = Uri.parse(zipUrl);

            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "pluginzip");
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

            if(downloadManager != null)
                downloadManager.enqueue(request);
        }
}
