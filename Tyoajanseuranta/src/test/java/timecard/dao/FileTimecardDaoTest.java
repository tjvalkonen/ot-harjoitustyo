package timecard.dao;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import timecard.domain.FakeUserDao;
import timecard.domain.Timecard;
import timecard.domain.User;


public class FileTimecardDaoTest {
    
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();    
  
    File timecardFile;  
    TimecardDao dao;    
    
    @Before
    public void setUp() throws Exception {
        timecardFile = testFolder.newFile("testfile_users.txt");  
        UserDao userDao = new FakeUserDao();
        userDao.create(new User("test", "Test User"));
        
        try (FileWriter file = new FileWriter(timecardFile.getAbsolutePath())) {
            file.write("0;0;60;1;Job;Test User\n");
        }
        
        dao = new FileTimecardDao(timecardFile.getAbsolutePath());        
    }
   
    @Test
    public void timecardsAreReadCorrectlyFromFile() {
        List<Timecard> timecards = dao.getAll();
        assertEquals(1, timecards.size());
        Timecard timecard = timecards.get(0);
        assertEquals(0, timecard.getId());
        assertEquals(0, timecard.getProjectId());
        assertEquals(60, timecard.getTime());
        assertEquals(1, timecard.getType());
        assertEquals("Job", timecard.getDescription());
        assertEquals("Test User", timecard.getUsername());
    }    
    
    @Test
    public void createdTimecardsAreListed() throws Exception {    
        dao.add(new Timecard(1,1,60,1,"Job 1", "Test User"));
        dao.add(new Timecard(2,1,30,2,"Job 2", "Test User"));
        
        List<Timecard> timecards = dao.getAll();
        assertEquals(3, timecards.size());
        Timecard timecard = timecards.get(1);
        assertEquals("Job 1", timecard.getDescription());
        assertEquals(2, timecard.getId());
        assertEquals("Test User", timecard.getUsername());
    }     
    
    @After
    public void tearDown() {
        timecardFile.delete();
    } 
}
