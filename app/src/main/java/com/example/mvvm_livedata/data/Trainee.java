package com.example.mvvm_livedata.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "trainee")
public class Trainee {

    @ColumnInfo(name = "traineeId")
    @PrimaryKey
    @NonNull
    private UUID id = UUID.randomUUID();

    @ColumnInfo(name = "name")
    private String name="";

    @ColumnInfo(name = "account")
    private String account="";

    @ColumnInfo(name = "graduate")
    private Boolean graduate = false;

    @ColumnInfo(name = "phone_number")
    private String phoneNumber = "";

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Boolean getGraduate() {
        return graduate;
    }

    public void setGraduate(Boolean graduate) {
        this.graduate = graduate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhotoFileName(){
        return "IMG_"+id+".jpg";
    }

}
