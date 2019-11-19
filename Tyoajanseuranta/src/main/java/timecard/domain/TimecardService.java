package timecard.domain;

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
    
    public boolean AddProject(String name) {
        
        Project project = new Project(name);
        try {   
            projectDao.add(project);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
    
}
