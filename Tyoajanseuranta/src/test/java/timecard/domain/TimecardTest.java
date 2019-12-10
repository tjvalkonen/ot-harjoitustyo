package timecard.domain;

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

    @Test
    public void addingProjectReturnsTrue() {
        assertEquals(true, timecardService.addProject("name"));
    }
    
    @Test
    public void getProjectsReturnsEmptyList() {
        assertEquals(true, timecardService.getProjects().isEmpty());
    }
    
    @Test
    public void addingTimecardReturnsTrue() {
        assertEquals(true, timecardService.addTimecard(1,1,1,"testi","user"));
    }
    
    @Test
    public void getTimecardsReturnsEmptyList() {
        assertEquals(true, timecardService.getTimecards(0).isEmpty());
    }
}
