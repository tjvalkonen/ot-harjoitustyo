package timecard.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import timecard.dao.ProjectDao;
import timecard.dao.TimecardDao;

/**
 * Sovelluslogiikka
 */

public class TimecardService {
    private ProjectDao projectDao;
    private TimecardDao timecardDao;
    
    private int projectId;
    
    public TimecardService(ProjectDao projectDao, TimecardDao timecardDao) {
        this.projectDao = projectDao;
        this.timecardDao = timecardDao;
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
    
    public boolean addTimecard(int projectId, int time, int type, String description) {
        
        Timecard timecard = new Timecard(projectId, time, type, description);
        try {   
            timecardDao.add(timecard);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    public List<Timecard> getTimecards(int projectId) {          
        return timecardDao.getAll()
            .stream()
            .filter(t-> t.getProjectId()==projectId)
            .collect(Collectors.toList());
    }
    
}
