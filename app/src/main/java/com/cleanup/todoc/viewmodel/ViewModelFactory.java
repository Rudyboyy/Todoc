package com.cleanup.todoc.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final TaskRepository taskDataSource;

    private final ProjectRepository projectDataSource;

    private final Executor executor;

   /* public static ViewModelFactory getInstance(Context context) {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                if (factory == null) {
                    factory = new ViewModelFactory(context);
                }
            }
        }
        return factory;
    }*/

    /*private ViewModelFactory(Context context) {
        TodocDatabase database = TodocDatabase.getDatabase(context);
        this.taskDataSource = new TaskRepository(application);
        this.projectDataSource = new ProjectRepository(application);
        this.executor = Executors.newSingleThreadExecutor();

    }*/

    public ViewModelFactory(TaskRepository taskRepository,
                            ProjectRepository projectRepository,
                            Executor executor) {
        this.taskDataSource = taskRepository;
        this.projectDataSource = projectRepository;
        this.executor = executor;
    }

    /*@Override
    @NotNull
    public <T extends ViewModel>  T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TodocViewModel.class)) {
            return (T) new TodocViewModel(application);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }*/

    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TodocViewModel.class)) {
            return (T) new TodocViewModel(taskDataSource, projectDataSource, executor);//todo ????? 
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
