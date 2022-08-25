package com.cleanup.todoc.injection;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;
import com.cleanup.todoc.viewmodel.TodocViewModel;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final TaskRepository taskDataSource;

    private final ProjectRepository projectDataSource;

    private final Executor executor;

    public ViewModelFactory(TaskRepository taskRepository,
                            ProjectRepository projectRepository,
                            Executor executor) {
        this.taskDataSource = taskRepository;
        this.projectDataSource = projectRepository;
        this.executor = executor;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TodocViewModel.class)) {
            return (T) new TodocViewModel(taskDataSource, projectDataSource, executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
