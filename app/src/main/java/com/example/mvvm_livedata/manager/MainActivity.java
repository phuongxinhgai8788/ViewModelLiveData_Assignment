package com.example.mvvm_livedata.manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.mvvm_livedata.R;
import com.example.mvvm_livedata.trainee_detail.TraineeDetailFragment;
import com.example.mvvm_livedata.trainee_list.TraineeListFragment;

import java.util.UUID;

public class MainActivity extends AppCompatActivity implements TraineeListFragment.Callbacks, TraineeDetailFragment.Callbacks {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment currentFragment =
                getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (currentFragment == null) {
            Fragment fragment = TraineeListFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    public void onFragmentBackPressed() {

        getSupportFragmentManager()
                .popBackStack();
    }

    @Override
    public void onTraineeSelected(UUID traineeId) {
        Fragment fragment = TraineeDetailFragment.newInstance(traineeId);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}