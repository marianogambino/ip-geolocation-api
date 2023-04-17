package ar.com.mercadolibre.ipgeolocation.common.util;

import com.google.gson.Gson;

public class JsonUtils {
    private static final Gson gson = new Gson();

    public static String toJson(Object object){
        return gson.toJson(object);
    }

    public static Object fromJson(String json, Class destinationClass){
        return gson.fromJson(json, destinationClass);
    }

}
