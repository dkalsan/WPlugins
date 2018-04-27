package com.dkalsan.wplugins.Main.PluginDownloaderAPI;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dkalsan.wplugins.Main.PluginDownloaderAPI.Model.Plugin;
import com.dkalsan.wplugins.R;

import java.util.List;

public class PluginsAdapter extends RecyclerView.Adapter<PluginsAdapter.ViewHolder> {
    private List<Plugin> plugins;
    private RequestManager glide;

    public PluginsAdapter(List<Plugin> plugins, RequestManager glide) {
        this.plugins = plugins;
        this.glide = glide;
    }

    @Override
    public PluginsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PluginsAdapter.ViewHolder holder, int position) {
        holder.titleTV.setText(plugins.get(position).getName());

        if(plugins.get(position).getScreenshots() != null) {
            String url = plugins.get(position).getScreenshots().get1().getSrc();
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH);

            glide.load(url).apply(options).into(holder.screenshotView);
        } else {
            glide.clear(holder.screenshotView);
            holder.screenshotView.setImageResource(R.drawable.default_screenshot);
        }
    }

    @Override
    public int getItemCount() {
        return plugins.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTV;
        private ImageView screenshotView;

        public ViewHolder(View view) {
            super(view);

            titleTV = view.findViewById(R.id.titleTV);
            screenshotView = view.findViewById(R.id.screenshotView);
        }
    }
}
