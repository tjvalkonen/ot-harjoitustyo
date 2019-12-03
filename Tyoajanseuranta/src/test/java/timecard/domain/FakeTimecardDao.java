package timecard.domain;

import timecard.dao.*;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import timecard.domain.Timecard;
import timecard.dao.TimecardDao;

public class FakeTimecardDao implements TimecardDao {
    List<Timecard> timecards;
    
    public FakeTimecardDao() {
        timecards = new ArrayList<>();
    }
    
    @Override
    public List<Timecard> getAll() {
        return timecards;
    }
    
    
    @Override
    public Timecard add(Timecard timecard) throws Exception {
        timecard.setId(timecards.size()+1);
        timecards.add(timecard);
        return timecard;
    }
}
