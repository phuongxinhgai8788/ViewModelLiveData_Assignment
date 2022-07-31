package com.example.mvvm_livedata.database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mvvm_livedata.data.Trainee;

import java.util.List;
import java.util.UUID;

@Dao
public interface TraineeDao {
    @Query("SELECT * FROM trainee")
    LiveData<List<Trainee>> getTraineeList();

    @Query("SELECT * FROM trainee WHERE traineeID=(:id)")
    LiveData<Trainee> getTrainee(UUID id);

    @Update
    void updateTrainee(Trainee trainee);

    @Insert
    void addTrainee(Trainee trainee);

    @Delete
    void deleteTrainee(Trainee trainee);
}
