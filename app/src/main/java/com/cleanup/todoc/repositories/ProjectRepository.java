package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.ProjectDao;
import com.cleanup.todoc.model.Project;

import java.util.List;

public class ProjectRepository {

    private final ProjectDao mProjectDao;

  /*  public ProjectRepository(Application application) {
        TodocDatabase db = TodocDatabase.getDatabase(application);
        mProjectDao = db.projectDao();
//        mTaskDao = db.taskDao();
    }*/

    public ProjectRepository(ProjectDao projectDao) { this.mProjectDao = projectDao; }

    public LiveData<List<Project>> getAllProject() {
        return mProjectDao.getAllProject();
    }

    public LiveData<Project> getProjectById(long projectId) { return this.mProjectDao.getProjectById(projectId); }

    public void createProject(Project project) {
        mProjectDao.createProject(project);
    }

//    public void delete(Project project) {
//        mProjectDao.delete(project.getId());
//    }
}
