package timecard.domain;

import timecard.dao.*;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import timecard.domain.Project;
import timecard.dao.ProjectDao;

public class FakeProjectDao implements ProjectDao {
    List<Project> projects;

    public FakeProjectDao() {
        projects = new ArrayList<>();
    }   
   
    @Override
    public List<Project> getAll() {
        return projects;
    }
    
    @Override
    public Project add(Project project) {
        project.setId(projects.size()+1);
        projects.add(project);
        return project;
    }   
}
