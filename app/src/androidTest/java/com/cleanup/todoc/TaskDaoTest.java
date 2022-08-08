package com.cleanup.todoc;

import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.cleanup.todoc.database.ProjectDao;
import com.cleanup.todoc.database.TaskDao;
import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    private TodocDatabase database;
    private TaskDao mTaskDao;
    private ProjectDao mProjectDao;
    private static final long PROJECT_ID = 1;
    private static final Project PROJECT_DEMO = new Project(PROJECT_ID, "Test", 0xFFEADAD1);
    private static final Task NEW_ITEM_PLACE_TO_VISIT = new Task(PROJECT_ID, "faire les tests", PROJECT_ID);
    private static final Task NEW_ITEM_IDEA = new Task(PROJECT_ID, "presentation des tests", PROJECT_ID);
    private static final Task NEW_ITEM_RESTAURANTS = new Task(PROJECT_ID, "execution des tests", PROJECT_ID);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {

        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                TodocDatabase.class)
                .allowMainThreadQueries()
                .build();
        /*Context context = androidx.test.core.app.ApplicationProvider.getApplicationContext();
        database = Room.inMemoryDatabaseBuilder(context, TodocDatabase.class).build();
        mTaskDao = database.taskDao();
        mProjectDao = database.projectDao();*/
    }

    @After
    public void closeDb() throws Exception {
        database.close();
    }

    @Test
    public void insertAndGetProject() throws InterruptedException {
        this.database.projectDao().createProject(PROJECT_DEMO);
        Project project = LiveDataTestUtil.getValue(this.database.projectDao().getProjectById(PROJECT_ID));
        assertTrue(project.getName().equals(PROJECT_DEMO.getName()) && project.getId() == PROJECT_ID);
    }

    @Test
    public void getTasksWhenNoTasksInserted() throws InterruptedException {
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasks(PROJECT_ID));
        assertTrue(tasks.isEmpty());
    }

    @Test
    public void insertAndGetTasks() throws InterruptedException {
        this.database.projectDao().createProject(PROJECT_DEMO);
        this.database.taskDao().insertTask(NEW_ITEM_PLACE_TO_VISIT);
        this.database.taskDao().insertTask(NEW_ITEM_IDEA);
        this.database.taskDao().insertTask(NEW_ITEM_RESTAURANTS);

        List<Task> items = LiveDataTestUtil.getValue(this.database.taskDao().getTasks(PROJECT_ID));
        assertTrue(items.size() == 3);
    }

    @Test
    public void insertAndDeleteTask() throws InterruptedException {
        this.database.projectDao().createProject(PROJECT_DEMO);
        this.database.taskDao().insertTask(NEW_ITEM_PLACE_TO_VISIT);
        Task taskAdded = LiveDataTestUtil.getValue(this.database.taskDao().getTasks(PROJECT_ID)).get(0);
        this.database.taskDao().deleteTask(taskAdded.getId());

        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasks(PROJECT_ID));
        assertTrue(tasks.isEmpty());
    }
}
