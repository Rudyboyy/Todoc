package com.cleanup.todoc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

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
    private static final long PROJECT_ID = 1;
    private static final Project PROJECT_DEMO = new Project(PROJECT_ID, "Test", 0xFFEADAD1);
    private static final Task TASK_DEMO = new Task(PROJECT_ID, "faire les tests", PROJECT_ID);
    private static final Task TASK_DEMO_2 = new Task(PROJECT_ID, "presentation des tests", PROJECT_ID);
    private static final Task TASK_DEMO_3 = new Task(PROJECT_ID, "execution des tests", PROJECT_ID);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {

        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                TodocDatabase.class)
                .allowMainThreadQueries()
                .build();
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
        this.database.taskDao().insertTask(TASK_DEMO);
        this.database.taskDao().insertTask(TASK_DEMO_2);
        this.database.taskDao().insertTask(TASK_DEMO_3);

        List<Task> items = LiveDataTestUtil.getValue(this.database.taskDao().getTasks(PROJECT_ID));
        assertEquals(3, items.size());
    }

    @Test
    public void insertAndDeleteTask() throws InterruptedException {
        this.database.projectDao().createProject(PROJECT_DEMO);
        this.database.taskDao().insertTask(TASK_DEMO);
        Task taskAdded = LiveDataTestUtil.getValue(this.database.taskDao().getTasks(PROJECT_ID)).get(0);
        this.database.taskDao().deleteTask(taskAdded.getId());

        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasks(PROJECT_ID));
        assertTrue(tasks.isEmpty());
    }
}
