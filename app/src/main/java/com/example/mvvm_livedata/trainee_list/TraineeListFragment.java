package com.example.mvvm_livedata.trainee_list;

import static java.util.Collections.emptyList;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.mvvm_livedata.R;
import com.example.mvvm_livedata.data.Trainee;
import com.example.mvvm_livedata.databinding.TraineeItemBinding;
import com.example.mvvm_livedata.trainee_detail.TraineeDetailViewModel;
import com.example.mvvm_livedata.utils.PictureUtils;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class TraineeListFragment extends Fragment {

    private static final String TAG = "TraineeListFragment";
    private TraineeListViewModel traineeListViewModel;
    private TraineeDetailViewModel traineeDetailViewModel;
    private TraineeAdapter adapter = new TraineeAdapter(emptyList());
    private RecyclerView recyclerView;

    private Context context;
    private Callbacks callback = null;

    public interface Callbacks{
        void onTraineeSelected(UUID traineeId);
    }
    public TraineeListFragment() {
        // Required empty public constructor
    }

    public static TraineeListFragment newInstance() {

        return new TraineeListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callback = (Callbacks)context;
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_trainee_list, container, false);
        recyclerView = view.findViewById(R.id.trainee_recycler_view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        traineeListViewModel = new ViewModelProvider(this).get(TraineeListViewModel.class);
        traineeDetailViewModel = new ViewModelProvider(this).get(TraineeDetailViewModel.class);
    }

    @Override
    public void onStart() {
        super.onStart();
        traineeListViewModel.traineeListLiveData.observe(getViewLifecycleOwner(), trainees -> {
            if(trainees != null){
                Log.i(TAG, "List size: "+trainees.size());
                adapter = new TraineeAdapter(trainees);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(adapter);
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_trainee_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.new_trainee){
            Trainee trainee = new Trainee();
            traineeListViewModel.addTrainee(trainee);
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }

    private class TraineeAdapter extends RecyclerView.Adapter<TraineeHolder>{

        private List<Trainee> trainees;

        public TraineeAdapter(List<Trainee> traineeList) {
            this.trainees = traineeList;
        }

        @NonNull
        @Override
        public TraineeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            TraineeItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.trainee_item, parent, false);
            return new TraineeHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull TraineeHolder holder, int position) {
            Trainee trainee = trainees.get(position);
            holder.bind(trainee);
        }

        @Override
        public int getItemCount() {
            return trainees.size();
        }
    }

    private class TraineeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Trainee trainee;
        private TraineeItemBinding binding;
        private File photoFile;

        public TraineeHolder(@NonNull TraineeItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        public void bind(Trainee trainee){
            this.trainee = trainee;
            traineeDetailViewModel.setTrainee(trainee);
            binding.setViewModel(traineeDetailViewModel);
            binding.setLifecycleOwner(TraineeListFragment.this);
            binding.executePendingBindings();

            if(!traineeDetailViewModel.getGraduate()){
                binding.graduateIv.setImageDrawable(null);
            }

            photoFile = traineeDetailViewModel.getPhotoFile();
            if(photoFile.exists()){
                Bitmap bitmap = PictureUtils.getScaledBitmap(photoFile.getPath(), requireActivity());
                binding.avatarIv.setImageBitmap(bitmap);
            }
        }

        @Override
        public void onClick(View view) {
            callback.onTraineeSelected(traineeDetailViewModel.getId());
        }
    }
}