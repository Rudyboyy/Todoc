package com.cleanup.todoc.viewmodel;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.R;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

public class TodocViewModel extends ViewModel {

    private final ProjectRepository mProjectRepository;
    private final TaskRepository taskDataSource;
    private final Executor executor;
    private SortMethod sortMethod = SortMethod.NONE;

    public LiveData<List<Project>> getListProjectLiveData() {
        return mProjectRepository.getAllProject();
    }

    public LiveData<List<Task>> getListTaskLiveData() {
        return taskDataSource.getAllTasks();
    }

    public TodocViewModel(TaskRepository taskDataSource, ProjectRepository projectDataSource, Executor executor) {
        this.taskDataSource = taskDataSource;
        this.mProjectRepository = projectDataSource;
        this.executor = executor;
    }

    public void createTask(Task task) {
        executor.execute(() -> taskDataSource.createTask(task));
    }

    public void deleteTask(Task task) {
        executor.execute(() -> taskDataSource.delete(task));
    }

    @SuppressLint("NonConstantResourceId")
    public void setSortMethod(int menuItemId) {
        switch (menuItemId) {
            case R.id.filter_alphabetical:
                sortMethod = SortMethod.ALPHABETICAL;
                break;
            case R.id.filter_alphabetical_inverted:
                sortMethod = SortMethod.ALPHABETICAL_INVERTED;
                break;
            case R.id.filter_oldest_first:
                sortMethod = SortMethod.OLD_FIRST;
                break;
            case R.id.filter_recent_first:
                sortMethod = SortMethod.RECENT_FIRST;
                break;
        }
    }

    public void updateTaskSortOrder(List<Task> tasks) {
        switch (sortMethod) {
            case ALPHABETICAL:
                Collections.sort(tasks, new Task.TaskAZComparator());
                break;
            case ALPHABETICAL_INVERTED:
                Collections.sort(tasks, new Task.TaskZAComparator());
                break;
            case RECENT_FIRST:
                Collections.sort(tasks, new Task.TaskRecentComparator());
                break;
            case OLD_FIRST:
                Collections.sort(tasks, new Task.TaskOldComparator());
                break;
        }
    }
}
