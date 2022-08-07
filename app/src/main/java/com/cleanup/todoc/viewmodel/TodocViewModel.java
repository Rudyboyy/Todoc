package com.cleanup.todoc.viewmodel;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TodocViewModel extends ViewModel {//todo remplacement AndroidViewModel par ViewModel   /!\

    private final ProjectRepository mProjectRepository;
    private final TaskRepository taskDataSource;
    private final Executor executor;

    /*public TodocViewModel(Application application) {
        super(application);
        mProjectRepository = new ProjectRepository(application);
        mListProjectLiveData = mProjectRepository.getAllProject();
        taskDataSource = new TaskRepository(application);
        mListTaskLiveData = taskDataSource.getTasks(PROJECT_ID);
    }*/

    public LiveData<List<Project>> getListProjectLiveData() {
        return mProjectRepository.getAllProject();
    }

    public LiveData<List<Task>> getListTaskLiveData() {
        return taskDataSource.getAllTasks();
    }

    @Nullable
    private LiveData<Project> currentProject;

    public TodocViewModel(TaskRepository taskDataSource, ProjectRepository projectDataSource, Executor executor) {
        this.taskDataSource = taskDataSource;
        this.mProjectRepository = projectDataSource;
        this.executor = executor;
    }

    public void init(long projectId) {
        if (this.currentProject != null) {
            return;
        }
        currentProject = mProjectRepository.getProjectById(projectId);
    }

    @Nullable
    public LiveData<Project> getCurrentProject() {
        return this.currentProject;
    }

    public LiveData<List<Task>> getTasks(long ProjectId) {
        return taskDataSource.getTasks(ProjectId);
    }

    public void createTask(Task task) {
        executor.execute(() -> taskDataSource.createTask(task));
    }

    public void deleteTask(long taskId) {
        executor.execute(() -> taskDataSource.deleteTask(taskId));
    }

    public void deleteTask(Task task) {
        executor.execute(() -> taskDataSource.delete(task));
    }
}
