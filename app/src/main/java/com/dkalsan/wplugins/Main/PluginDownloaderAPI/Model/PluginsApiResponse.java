package com.dkalsan.wplugins.Main.PluginDownloaderAPI.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PluginsApiResponse {
    @SerializedName("plugins")
    @Expose
    private List<Plugin> plugins = null;

    public List<Plugin> getPlugins() {
        return plugins;
    }

    public void setPlugins(List<Plugin> plugins) {
        this.plugins = plugins;
    }
}
