package timecard.domain;

import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import timecard.dao.ProjectDao;
import timecard.dao.TimecardDao;
import timecard.dao.UserDao;

/**
 * Sovelluslogiikka
 */

public class TimecardService {
    private ProjectDao projectDao;
    private TimecardDao timecardDao;
    private UserDao userDao;
    private User loggedIn;
    
    private int projectId;
    
    public TimecardService(ProjectDao projectDao, TimecardDao timecardDao, UserDao userDao) {
        this.projectDao = projectDao;
        this.timecardDao = timecardDao;
        this.userDao = userDao;
    }
    
    /**
    * Uuden projektin lisääminen
    *
    * @param   name   projektin nimi
    * @param   etc   projektin arvioitu kokonaistyöaika
    */
    
    public boolean addProject(String name, int etc) {
        
        Project project = new Project(name, etc);
        try {   
            projectDao.add(project);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    /**
    * Kaikkien projektien hakeminen listaan
    *
    */
    
    public List<Project> getProjects() {
          
        List<Project> projects = projectDao.getAll()
            .stream()
            .collect(Collectors.toList());
        
        Collections.reverse(projects);
        
        return projects;
    }
    
    /**
    * Uuden työajan lisääminen
    *
    */
    
    public boolean addTimecard(int projectId, int time, int type, String description, String username) {
        
        Timecard timecard = new Timecard(projectId, time, type, description, username);
        try {   
            timecardDao.add(timecard);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    /**
    * Projektin kaikkien työaikamerkintöjen hakeminen listaan
    *
    */ 
    
    public List<Timecard> getTimecards(int projectId) {          
        List<Timecard> timecards = timecardDao.getAll()
            .stream()
            .filter(t-> t.getProjectId() == projectId)
            .collect(Collectors.toList());
        Collections.reverse(timecards);
        
        return timecards;
    }

    /**
    * Projektin kokonaistyöajan hakeminen tekstimuodossa
    *
    */  
    
    public String getProjectTotalTime(int projectId) {          
        List<Timecard> timecards = timecardDao.getAll()
            .stream()
            .filter(t-> t.getProjectId() == projectId)
            .collect(Collectors.toList());
        int time = 0;
        for (Timecard t : timecards) {
            time += t.getTime();
        }
        
        int hours = time / 60;
        int minutes = time % 60;
        String timeString = Integer.toString(hours) + "h " + Integer.toString(minutes) + "min";
        
        return timeString;
    }

    /**
    * Projektin tietyn työtyypin kokonaistyöajan hakeminen tekstimuodossa
    *
    */ 
    
    public String getProjectJobTypeTime(int projectId, int type) {          
        List<Timecard> timecards = timecardDao.getAll()
            .stream()
            .filter(t-> t.getProjectId() == projectId)
            .filter(t-> t.getType() == type)
            .collect(Collectors.toList());
        int time = 0;
        for (Timecard t : timecards) {
            time += t.getTime();
        }
        
        int hours = time / 60;
        int minutes = time % 60;
        String timeString = Integer.toString(hours) + "h " + Integer.toString(minutes) + "min";
        
        return timeString;
    }
  
    /**
    * Projektin tietyn työtyypin kokonaistyöajan hakeminen numeromuodossa minuuteina
    *
    */
    
    public int getProjectJobTypeTimeInt(int projectId, int type) {          
        List<Timecard> timecards = timecardDao.getAll()
            .stream()
            .filter(t-> t.getProjectId() == projectId)
            .filter(t-> t.getType() == type)
            .collect(Collectors.toList());
        int time = 0;
        for (Timecard t : timecards) {
            time += t.getTime();
        }
        
        return time;
    }
        
    /**
    * sisäänkirjautuminen
    * 
    * @param   username   käyttäjätunnus
    * 
    * @return true jos käyttäjätunnus on olemassa, muuten false 
    */    
    
    public boolean login(String username) {
        User user = userDao.findByUsername(username);
        if (user == null) {
            return false;
        }
        
        loggedIn = user;
        
        return true;
    }
    
    /**
    * kirjautuneena oleva käyttäjä
    * 
    * @return kirjautuneena oleva käyttäjä 
    */   
    
    public User getLoggedUser() {
        return loggedIn;
    }
   
    /**
    * uloskirjautuminen
    */  
    
    public void logout() {
        loggedIn = null;  
    }
    
    /**
    * uuden käyttäjän luominen
    * 
    * @param   username   käyttäjätunnus
    * @param   name   käyttäjän nimi
    * 
    * @return true jos käyttäjätunnus on luotu, muuten false 
    */ 
    
    public boolean createUser(String username, String name)  {   
        if (userDao.findByUsername(username) != null) {
            return false;
        }
        User user = new User(username, name);
        try {
            userDao.create(user);
        } catch (Exception e) {
            return false;
        }

        return true;
    }    
}
