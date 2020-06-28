package org.hello.core.framework.helper;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.ws.rs.core.Response;

public class TestHelper {

    public static JsonObject getJsonFromResponse(Response res) {
        Gson gson = new Gson();
        return gson.fromJson(res.readEntity(String.class), JsonObject.class);
    }

    public static <T> T deserializeJson(JsonObject json, Class<T> requiredClass) {
        Gson gson = new Gson();
        return (T)gson.fromJson(json, requiredClass);
    }

    public static <T> T deserializeJson(Response res, Class<T> requiredClass) {
        JsonObject json = getJsonFromResponse(res);
        Gson gson = new Gson();
        return (T)gson.fromJson(json, requiredClass);
    }

    public static String serializeToJson(Object requiredClass) {
        Gson gson = new Gson();
        return gson.toJson(requiredClass);
    }
}
