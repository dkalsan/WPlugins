package com.dkalsan.wplugins.Main.PluginDownloaderAPI;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class CustomTypeAdapterFactory implements TypeAdapterFactory {
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
        return new TypeAdapter<T>() {
            public void write(JsonWriter out, T value) throws IOException {
                try {
                    delegate.write(out, value);
                } catch (IOException e) {
                    delegate.write(out, null);
                }
            }
            public T read(JsonReader in) throws IOException {
                try {
                    return delegate.read(in);
                } catch (IOException e) {
                    Log.w("TypeAdapterFactory", "IOException");
                    in.skipValue();
                    return null;
                } catch (IllegalStateException e) {
                    Log.w("TypeAdapterFactory", "IllegalStateException");
                    in.skipValue();
                    return null;
                } catch (JsonSyntaxException e) {
                    Log.w("TypeAdapterFactory", "JsonSyntaxException");
                    in.skipValue();
                    return null;
                }
            }
        };
    }
}
