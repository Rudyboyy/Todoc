package com.cleanup.todoc.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.ProjectDao;
import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Project;

import java.util.List;

public class TodocRepository {

    private ProjectDao mProjectDao;
//    private TaskDao mTaskDao; todo
    private LiveData<List<Project>> mListProjectLiveData;

    public TodocRepository(Application application) {
        TodocDatabase db = TodocDatabase.getDatabase(application);
        mProjectDao = db.projectDao();
    }

    public LiveData<List<Project>> getAllProject() {
        return mProjectDao.getAllProject();
    }
}
