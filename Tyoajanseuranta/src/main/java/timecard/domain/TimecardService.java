package timecard.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import timecard.dao.ProjectDao;

/**
 * Sovelluslogiikka
 */

public class TimecardService {
    private ProjectDao projectDao;
    
    public TimecardService(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }
    
    /**
    * Uuden projektin lisääminen
    *
    * @param   name   projektin nimi
    */
    
    public boolean addProject(String name) {
        
        Project project = new Project(name);
        try {   
            projectDao.add(project);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    public List<Project> getProjects() {
//        if (loggedIn == null) {
//            return new ArrayList<>();
//        }
          
        return projectDao.getAll()
            .stream()
//            .filter(t-> t.getUser().equals(loggedIn))
//            .filter(t->!t.isDone())
            .collect(Collectors.toList());
    }
    
}
