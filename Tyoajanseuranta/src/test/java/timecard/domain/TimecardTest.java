package timecard.domain;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class TimecardTest {
    
    TimecardService timecardService;
    FakeProjectDao projectDao;
    FakeTimecardDao timecardDao;
    FakeUserDao userDao;
    
    @Before
    public void setUp() {
        projectDao = new FakeProjectDao();
        timecardDao = new FakeTimecardDao();
        userDao = new FakeUserDao();
        timecardService = new TimecardService(projectDao, timecardDao, userDao);
    }

    // User creation, login & logout tests
    
    @Test
    public void nonExistingUserCantLogin() {
        boolean result = timecardService.login("nonexisting");
        assertFalse(result);      
        assertEquals(null, timecardService.getLoggedUser());
    }    
    
    @Test
    public void userCanLogIn() {
        boolean result = timecardService.login("testuser");
        assertTrue(result);     
        User loggedIn = timecardService.getLoggedUser();
        assertEquals("Test User", loggedIn.getName() );
    }
    
    @Test
    public void userCanLogout() {
        timecardService.login("testuser");
        timecardService.logout();     
        assertEquals(null, timecardService.getLoggedUser());
    }    
    
    @Test
    public void canNotCreateUserIfUsernameExists() throws Exception {
        boolean result = timecardService.createUser("testuser", "Test User");
        assertFalse(result);
    }
    
    @Test
    public void newUserCanLogin() throws Exception {
        boolean result = timecardService.createUser("koodaaja", "Kalle koodaaja");
        assertTrue(result);   
        boolean loginOk = timecardService.login("koodaaja");
        assertTrue(loginOk);      
        User loggedIn = timecardService.getLoggedUser();
        assertEquals("Kalle koodaaja", loggedIn.getName() );
    }  
    
    // Project tests
    
    @Test
    public void addingProjectReturnsTrue() {
        assertEquals(true, timecardService.addProject("name", 60));
    }
    
    @Test
    public void getProjectsReturnsEmptyList() {
        assertEquals(true, timecardService.getProjects().isEmpty());
    }

    @Test
    public void getProjectsReturnsList() {
        timecardService.addProject("name", 60);
        assertEquals(false, timecardService.getProjects().isEmpty());
    }
    
    @Test
    public void getProjectTotalTimeReturnsString() {
        timecardService.addProject("name", 60);
        timecardService.addTimecard(0, 60, 1, "description", "testuser");
        assertEquals("1h 0min", timecardService.getProjectTotalTime(0));
    }
    
    @Test
    public void getProjectJobTypeTimeReturnsString() {
        timecardService.addProject("name", 60);
        timecardService.addTimecard(0, 60, 1, "description", "testuser");
        assertEquals("1h 0min", timecardService.getProjectJobTypeTime(0,1));
    }
    
    @Test
    public void getProjectEtcReturnsString() {
        timecardService.addProject("name", 60);
        List<Project> projects = timecardService.getProjects();
        Project project = projects.get(0);
        assertEquals("1h 0min" , project.getEtcString());
    }
    
    @Test
    public void getProjectEtcReturnInt() {
        timecardService.addProject("name", 60);
        List<Project> projects = timecardService.getProjects();
        Project project = projects.get(0);
        assertEquals(60 , project.getEtc());
    }
    
    @Test
    public void projectEquals() {
        timecardService.addProject("name", 60);
        List<Project> projects = timecardService.getProjects();
        Project project = projects.get(0);
        assertEquals(true, project.equals(project));
    }
    
    @Test
    public void projectNotEquals() {
        timecardService.addProject("name", 60);
        timecardService.addProject("name2", 30);
        List<Project> projects = timecardService.getProjects();
        Project project1 = projects.get(0);
        Project project2 = projects.get(1);
        assertEquals(false, project1.equals(project2));
    }
    
    @Test
    public void projectNameWorks() {
        Project project = new Project("name", 60);
        assertEquals("name", project.getName());
    }   
    
    
    // Timecard tests
    
    @Test
    public void addingTimecardReturnsTrue() {
        assertEquals(true, timecardService.addTimecard(0,1,1,"testi","testuser"));
    }
    
    @Test
    public void getTimecardsReturnsEmptyList() {
        assertEquals(true, timecardService.getTimecards(0).isEmpty());
    }
    
    @Test
    public void getTimecardsReturnsList() {
        timecardService.addProject("name", 60);
        timecardService.addTimecard(0,60,1,"suunnittelu","testuser");
        timecardService.addTimecard(0,60,2,"ohjelmointi","testuser");    
        assertEquals(false, timecardService.getTimecards(0).isEmpty());
    }
    
    @Test
    public void getProjectjobTypeTimeRetunsOk() {
        timecardService.addProject("name", 60);
        timecardService.addTimecard(0,60,1,"suunnittelu","testuser");
        timecardService.addTimecard(0,30,2,"ohjelmointi","testuser");    
        assertEquals(60, timecardService.getProjectJobTypeTimeInt(0,1));
    } 
    
}
