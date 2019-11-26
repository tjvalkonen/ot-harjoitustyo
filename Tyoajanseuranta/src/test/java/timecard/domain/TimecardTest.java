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
    
    @Before
    public void setUp() {
        projectDao = new FakeProjectDao();
        timecardService = new TimecardService(projectDao);
    }

    @Test
    public void addingProjectReturnsTrue() {
        assertEquals(true, timecardService.addProject("name"));
    }
}
