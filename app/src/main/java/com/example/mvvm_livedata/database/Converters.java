package com.example.mvvm_livedata.database;

import androidx.room.TypeConverter;

import java.util.UUID;

public class Converters {
    @TypeConverter
    public UUID toUUID(String uuid){
        return UUID.fromString(uuid);
    }

    @TypeConverter
    public String fromUUID(UUID uuid){
        return uuid.toString();
    }

}
