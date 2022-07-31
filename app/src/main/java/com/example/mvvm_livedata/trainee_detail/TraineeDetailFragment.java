package com.example.mvvm_livedata.trainee_detail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.mvvm_livedata.R;
import com.example.mvvm_livedata.data.Trainee;
import com.example.mvvm_livedata.databinding.FragmentTraineeDetailBinding;
import com.example.mvvm_livedata.utils.PictureUtils;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class TraineeDetailFragment extends Fragment{

    private static final int REQUEST_PHOTO = 1;
    private static final String ARG_PARAM = "trainee_id";
    private static final String TAG = "TraineeDetailFragment";

    private EditText nameET, accountET, phoneET;
    private CheckBox graduateCheckBox;
    private Button saveBtn, cancelBtn;
    private ImageButton takePhotoImageBtn;
    private ImageView avatarIV;
    private TraineeDetailViewModel traineeDetailViewModel;
    private Callbacks callbacks;
    private FragmentTraineeDetailBinding binding;

    private Trainee trainee;
    private File photoFile;
    private Uri photoUri;
    private UUID traineeUUID;


    public interface Callbacks {
        void onFragmentBackPressed();
    }
    public TraineeDetailFragment() {
        // Required empty public constructor
    }

    public static TraineeDetailFragment newInstance(UUID traineeId) {
        TraineeDetailFragment fragment = new TraineeDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM, traineeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            traineeUUID = (UUID)getArguments().getSerializable(ARG_PARAM);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callbacks = (Callbacks) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_trainee_detail, container, false);
        binding.setLifecycleOwner(this);
        nameET = binding.nameET;
        accountET = binding.accountET;
        phoneET = binding.phoneET;
        avatarIV = binding.avatar;
        takePhotoImageBtn = binding.openCameraBtn;
        saveBtn = binding.saveBtn;
        cancelBtn = binding.cancelBtn;
        graduateCheckBox = binding.graduateCheckbox;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        traineeDetailViewModel = new ViewModelProvider(this).get(TraineeDetailViewModel.class);
        traineeDetailViewModel.loadTrainee(traineeUUID);
        binding.setViewModel(traineeDetailViewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        traineeDetailViewModel.traineeLiveData.observe(
                getViewLifecycleOwner(), trainee -> {
                    if(trainee!=null){
                        this.trainee = trainee;
                        Log.i(TAG, "114");
                        traineeDetailViewModel.setTrainee(trainee);
                        binding.setViewModel(traineeDetailViewModel);
                        binding.setLifecycleOwner(getViewLifecycleOwner());
                        photoFile = traineeDetailViewModel.getPhotoFile();
                        photoUri = FileProvider.getUriForFile(requireActivity(),
                                "com.example.mvvm_livedata.fileprovider", photoFile);
                        updatePhotoView();
                    }else{
                        this.trainee = new Trainee();
                    }
                }
        );

    }

    private void updatePhotoView() {
        if(photoFile.exists()){
            Bitmap bitmap = PictureUtils.getScaledBitmap(photoFile.getPath(), requireActivity());
            avatarIV.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        PackageManager packageManager = requireActivity().getPackageManager();

        Intent captureImageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        ResolveInfo resolvedActivity = packageManager.resolveActivity(captureImageIntent,
                PackageManager.MATCH_DEFAULT_ONLY);
        if( resolvedActivity == null) {
            takePhotoImageBtn.setEnabled(false);
        }
        takePhotoImageBtn.setOnClickListener( v -> {
            captureImageIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);

            List<ResolveInfo> cameraActivities = packageManager.queryIntentActivities(captureImageIntent,
                    PackageManager.MATCH_DEFAULT_ONLY);

            for(ResolveInfo cameraActivity: cameraActivities){
                requireActivity().grantUriPermission(
                        cameraActivity.activityInfo.packageName,
                        photoUri,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                );
            }
            startActivityForResult(captureImageIntent, REQUEST_PHOTO);
        });

        saveBtn.setOnClickListener(view->{
            traineeDetailViewModel.updateTrainee();
            callbacks.onFragmentBackPressed();
        });

        cancelBtn.setOnClickListener(view -> {
            traineeDetailViewModel.deleteTrainee();
            callbacks.onFragmentBackPressed();
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode != Activity.RESULT_OK) {
            return;
        }else if(requestCode == REQUEST_PHOTO){
            requireActivity().revokeUriPermission(photoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
        // Revoke photo permissions if the user leaves without taking a photo
        requireActivity().revokeUriPermission(photoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
    }
}