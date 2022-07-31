package com.example.mvvm_livedata.data;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.mvvm_livedata.database.Database;
import com.example.mvvm_livedata.database.TraineeDao;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {

    private static Repository INSTANCE;
    private TraineeDao traineeDao;
    private File filesDir;
    private Executor executor = Executors.newSingleThreadExecutor();

    private Repository(File filesDir, TraineeDao traineeDao){

        this.traineeDao = traineeDao;
        this.filesDir = filesDir;
    }

    public static Repository get(){
        if(INSTANCE == null){
           throw new IllegalStateException("Repository must be initialized");
        }
        return INSTANCE;
    }

    public static void initialize(File filesDir, TraineeDao traineeDao){
        INSTANCE = new Repository(filesDir, traineeDao);
    }

    public LiveData<List<Trainee>> getTraineeList(){
        LiveData<List<Trainee>> trainees = null;
        if(traineeDao != null){
            trainees = traineeDao.getTraineeList();
        }
        return trainees;
    }

    public LiveData<Trainee> getTrainee(UUID id){
        LiveData<Trainee> trainee = null;
        if(traineeDao != null){
            trainee = traineeDao.getTrainee(id);
        }
        return trainee;
    }
    
    public File getPhotoFile(Trainee trainee){
        File file = new File(filesDir, trainee.getPhotoFileName());
        return file;
    }

    public void addTrainee(Trainee trainee){
        executor.execute(() ->traineeDao.addTrainee(trainee));
    }

    public void updateTrainee(Trainee trainee){
        Thread thread = new Thread(() -> traineeDao.updateTrainee(trainee));
        thread.start();
    }

    public void deleteTrainee(Trainee trainee){
//        Thread thread = new Thread(() -> traineeDao.deleteTrainee(trainee));
//        thread.start();

        executor.execute(() ->traineeDao.deleteTrainee(trainee));

    }
}
