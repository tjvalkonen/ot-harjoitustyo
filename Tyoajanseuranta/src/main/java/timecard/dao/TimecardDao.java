package timecard.dao;

import java.util.List;
import timecard.domain.Timecard;

public interface TimecardDao {
    
    Timecard add(Timecard timecard) throws Exception;

    List<Timecard> getAll();
    
}
