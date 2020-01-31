package com.example.android.stakdice.converters;

import androidx.room.TypeConverter;

import com.example.android.stakdice.gsonconverters.PropertyBasedInterfaceMarshal;
import com.example.android.stakdice.models.attribute.Attribute;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class StakConverters {

    @TypeConverter
    public static Attribute fromString(String value) {
        Gson gson = getGson();
        return gson.fromJson(value, Attribute.class);
    }

    @TypeConverter
    public static String attributeToString(Attribute attr) {
        Gson gson = getGson();
        return gson.toJson(attr);
    }

    private static Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Attribute.class, new PropertyBasedInterfaceMarshal())
                .create();
    }

}
