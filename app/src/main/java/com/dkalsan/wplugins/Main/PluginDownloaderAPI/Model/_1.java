package com.dkalsan.wplugins.Main.PluginDownloaderAPI.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class _1 {
    @SerializedName("src")
    @Expose
    private String src;
    @SerializedName("caption")
    @Expose
    private String caption;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
