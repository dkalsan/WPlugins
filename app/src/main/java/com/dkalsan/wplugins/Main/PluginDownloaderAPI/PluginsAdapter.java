package com.dkalsan.wplugins.Main.PluginDownloaderAPI;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dkalsan.wplugins.Main.PluginDownloaderAPI.Model.Plugin;
import com.dkalsan.wplugins.R;

import java.util.List;

public class PluginsAdapter extends RecyclerView.Adapter<PluginsAdapter.ViewHolder> {
    private List<Plugin> plugins;

    public PluginsAdapter(List<Plugin> plugins) {
        this.plugins = plugins;
    }

    @Override
    public PluginsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PluginsAdapter.ViewHolder holder, int position) {
        holder.titleTV.setText(plugins.get(position).getName());
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
