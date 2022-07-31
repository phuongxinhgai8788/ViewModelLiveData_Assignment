package com.example.mvvm_livedata.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.mvvm_livedata.data.Trainee;

@androidx.room.Database(entities = {Trainee.class}, version = 1, exportSchema = true)
@TypeConverters(Converters.class)
public abstract class Database extends RoomDatabase {
    public abstract TraineeDao traineeDao();

    private static volatile Database INSTANCE;

    public static Database getInstance(Context context) {
        if(INSTANCE == null){
            synchronized (Database.class){
                INSTANCE = Room.databaseBuilder(context, Database.class, "database")
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return INSTANCE;
    }
    public static void destroy(){
        INSTANCE = null;
    }
}
