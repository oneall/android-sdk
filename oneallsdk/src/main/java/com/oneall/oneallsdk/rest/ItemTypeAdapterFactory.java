package com.oneall.oneallsdk.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by urk on 9/3/15.
 */
public class ItemTypeAdapterFactory implements TypeAdapterFactory {

    public <T> TypeAdapter<T> create(Gson gson, final TypeToken<T> type) {

        final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
        final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);

        return new TypeAdapter<T>() {

            public void write(JsonWriter out, T value) throws IOException {
                delegate.write(out, value);
            }

            public T read(JsonReader in) throws IOException {

                JsonElement jsonElement = elementAdapter.read(in);
                if (jsonElement.isJsonObject()) {
                    JsonObject responseJobj = jsonElement.getAsJsonObject();
                    if (responseJobj.has("response") && responseJobj.get("response").isJsonObject()) {
                        jsonElement = responseJobj.get("response");
                        if (jsonElement.isJsonObject()) {
                            JsonObject resultJobj = jsonElement.getAsJsonObject();
                            if (resultJobj.has("result") && resultJobj.get("result").isJsonObject()) {
                                jsonElement = resultJobj.get("result");
                            } else if (resultJobj.has("request") && resultJobj.get("request").isJsonObject()) {
                                jsonElement = resultJobj.get("request");
                            }
                        }
                    }
                }

                return delegate.fromJsonTree(jsonElement);
            }
        }.nullSafe();
    }
}