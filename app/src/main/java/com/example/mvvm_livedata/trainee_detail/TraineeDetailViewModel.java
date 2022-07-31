package com.example.mvvm_livedata.trainee_detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.mvvm_livedata.data.Repository;
import com.example.mvvm_livedata.data.Trainee;

import java.io.File;
import java.util.UUID;

public class TraineeDetailViewModel extends ViewModel {

    private static final String TAG = "TraineeDetailViewModel";
    public MutableLiveData<String> nameMutable = new MutableLiveData<>();
    public MutableLiveData<String> accountMutable = new MutableLiveData<>();
    public MutableLiveData<Boolean> graduateMutable = new MutableLiveData<>();
    public MutableLiveData<String> phoneNumberMutable = new MutableLiveData<>();
    public MutableLiveData<UUID> traineeIdLiveData = new MutableLiveData<>();
    private Repository repository = Repository.get();
    public LiveData<Trainee> traineeLiveData = Transformations.switchMap(traineeIdLiveData, traineeId -> repository.getTrainee(traineeId));
    private Trainee trainee = new Trainee();

    public void loadTrainee(UUID traineeId) {
        traineeIdLiveData.setValue(traineeId);
    }

    public void setTrainee(Trainee trainee) {
        this.trainee = trainee;
        nameMutable.setValue(trainee.getName());
        accountMutable.setValue(trainee.getAccount());
        graduateMutable.setValue(trainee.getGraduate());
        phoneNumberMutable.setValue(trainee.getPhoneNumber());
    }

    public void updateTrainee() {
        trainee.setGraduate(graduateMutable.getValue());
        trainee.setAccount(accountMutable.getValue());
        trainee.setName(nameMutable.getValue());
        trainee.setPhoneNumber(phoneNumberMutable.getValue());
        repository.updateTrainee(trainee);
    }

    public void deleteTrainee() {
        repository.deleteTrainee(trainee);
    }

    public File getPhotoFile() {
        return repository.getPhotoFile(trainee);
    }

    public UUID getId() {
        return trainee.getId();
    }

    public String getName() {
        return nameMutable.getValue();
    }

    public void setName(String name) {
        this.nameMutable.setValue(name);
    }

    public String getAccount() {
        return accountMutable.getValue();
    }

    public void setAccount(String account) {
        this.accountMutable.setValue(account);
    }

    public Boolean getGraduate() {
        return graduateMutable.getValue();
    }

    public void setGraduate(Boolean graduate) {
        this.graduateMutable.setValue(graduate);
    }

    public String getPhoneNumber() {
        return phoneNumberMutable.getValue();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumberMutable.setValue(phoneNumber);
    }

    public String getPhotoFileName() {
        return trainee.getPhotoFileName();
    }
}
