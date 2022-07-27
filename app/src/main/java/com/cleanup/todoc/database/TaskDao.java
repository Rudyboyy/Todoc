package com.cleanup.todoc.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createTask(Task task);

    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getAllTask();

    @Query("SELECT * FROM Task WHERE id = :taskId")
    LiveData<Task> getTaskById(long taskId);

/*    @Delete
    void deleteTask(Task task);*/

    @Query("DELETE FROM Task WHERE id = :taskId")
    void deleteTaskById(long taskId);

    @Query("DELETE FROM Task")
    void deleteTask(Task task);
}