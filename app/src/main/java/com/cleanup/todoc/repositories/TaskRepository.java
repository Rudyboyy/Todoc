package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskRepository {

    private final TaskDao taskDao;

    public TaskRepository(TaskDao taskDao) { this.taskDao = taskDao; }

    public LiveData<List<Task>> getTasks(long projectId){ return this.taskDao.getTasks(projectId); }

    public void createTask(Task task){ taskDao.insertTask(task); }

    public LiveData<List<Task>> getAllTasks() {
        return taskDao.getAllTasks();
    }

    public void delete(Task task) {
        taskDao.deleteTask(task.getId());
    }
}
