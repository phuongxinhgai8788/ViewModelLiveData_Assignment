package com.example.mvvm_livedata.trainee_list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mvvm_livedata.data.Repository;
import com.example.mvvm_livedata.data.Trainee;

import java.util.List;

public class TraineeListViewModel extends ViewModel {
    private Repository repository = Repository.get();

    LiveData<List<Trainee>> traineeListLiveData = repository.getTraineeList();

    public void addTrainee(Trainee trainee){
        repository.addTrainee(trainee);
    }
}
