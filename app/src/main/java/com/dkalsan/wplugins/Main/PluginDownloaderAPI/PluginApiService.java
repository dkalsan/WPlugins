package com.dkalsan.wplugins.Main.PluginDownloaderAPI;

import com.dkalsan.wplugins.Main.PluginDownloaderAPI.Model.PluginsApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PluginApiService {
    @GET("?action=query_plugins&request%5bpage%5d=1&request%5bbrowse%5d=new")
    Call<PluginsApiResponse> getPluginsResponse(@Query("request[per_page]") String per_page);
}
