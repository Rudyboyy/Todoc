package com.cleanup.todoc.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.repository.TodocRepository;

import java.util.List;

public class TodocViewModel extends AndroidViewModel {

    private TodocRepository mTodocRepository;
    private LiveData<List<Project>> mListProjectLiveData;

    public TodocViewModel(@NonNull Application application) {
        super(application);
        mTodocRepository = new TodocRepository(application);
        mListProjectLiveData = mTodocRepository.getAllProject();
    }

    public LiveData<List<Project>> getListProjectLiveData() {
        return mListProjectLiveData;
    }
}
