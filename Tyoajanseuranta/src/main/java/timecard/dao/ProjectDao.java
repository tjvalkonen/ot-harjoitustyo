package timecard.dao;

import java.util.List;
import timecard.domain.Project;

public interface ProjectDao {
    
    Project add(Project project) throws Exception;

    List<Project> getAll();
    
}
