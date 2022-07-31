package com.example.mvvm_livedata.manager;

import android.app.Application;

import com.example.mvvm_livedata.data.Repository;
import com.example.mvvm_livedata.database.Database;
import com.example.mvvm_livedata.database.TraineeDao;

import java.io.File;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Database database = Database.getInstance(this);
        TraineeDao traineeDao = database.traineeDao();
        File filesDir = this.getFilesDir();
        Repository.initialize(filesDir, traineeDao);
    }
}
