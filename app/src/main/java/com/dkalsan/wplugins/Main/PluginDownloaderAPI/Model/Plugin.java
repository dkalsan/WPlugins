package com.dkalsan.wplugins.Main.PluginDownloaderAPI.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Plugin {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("homepage")
    @Expose
    private String homepage;
    @SerializedName("screenshots")
    @Expose
    private Screenshots screenshots;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public Screenshots getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(Screenshots screenshots) {
        this.screenshots = screenshots;
    }
}
