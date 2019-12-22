package timecard.dao;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import timecard.domain.User;

public class FileUserDaoTest {
    
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    
    File userFile;  
    UserDao dao;
    
    @Before
    public void setUp() throws Exception {
        userFile = testFolder.newFile("testfile_users.txt");         
        try (FileWriter file = new FileWriter(userFile.getAbsolutePath())) {
            file.write("testuser;Test User\n");
        }     
        dao = new FileUserDao(userFile.getAbsolutePath());
    }
   
    @Test
    public void usersAreReadCorrectlyFromFile() {
        List<User> users = dao.getAll();
        assertEquals(1, users.size());
        User user = users.get(0);
        assertEquals("Test User", user.getName());
        assertEquals("testuser", user.getUsername());
    }
    
    @Test
    public void existingUserIsFound() {
        User user = dao.findByUsername("testuser");
        assertEquals("Test User", user.getName());
        assertEquals("testuser", user.getUsername());
    }
    
    @Test
    public void nonExistingUserIsNotFound() {
        User user = dao.findByUsername("Tomas");
        assertEquals(null, user);
    }
  
    @Test
    public void newUserIsFound() throws Exception {
        dao.create(new User("Tomas", "Tomas Valkonen"));       
        User user = dao.findByUsername("Tomas");
        assertEquals("Tomas Valkonen", user.getName());
        assertEquals("Tomas", user.getUsername());
    }
    
    @After
    public void tearDown() {
        userFile.delete();
    }
}
