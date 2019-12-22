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
import timecard.domain.FakeProjectDao;
import timecard.domain.Project;

public class FileProjectDaoTest {
    
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();    
  
    File projectFile;  
    ProjectDao dao;    
    
    @Before
    public void setUp() throws Exception {
        projectFile = testFolder.newFile("testfile_projects.txt");
        
        try (FileWriter file = new FileWriter(projectFile.getAbsolutePath())) {
            file.write("0;Test Project 1;60\n");
        }
        
        dao = new FileProjectDao(projectFile.getAbsolutePath());        
    }
    
    @Test
    public void timecardsAreReadCorrectlyFromFile() {
        List<Project> projects = dao.getAll();
        assertEquals(1, projects.size());
        Project project = projects.get(0);
        assertEquals(0, project.getId());
        assertEquals(60, project.getEtc());
    } 
    
    @Test
    public void createdTimecardsAreListed() throws Exception {    
        dao.add(new Project(1,"Test Project 2",30));
        dao.add(new Project(2,"Test Project 3",120));
        
        List<Project> projects = dao.getAll();
        assertEquals(3, projects.size());
        Project project = projects.get(1);
        assertEquals("Test Project 2", project.getName());
        assertEquals(2, project.getId());
    }   
    
    
    @After
    public void tearDown() {
        projectFile.delete();
    } 
    
}
