package timecard.dao;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import timecard.domain.Project;
import timecard.domain.Timecard;

public class FileTimecardDao implements TimecardDao {
    public List<Timecard> timecards;
    private String file;
    
    public FileTimecardDao(String file) throws Exception {
        timecards = new ArrayList<>();
        this.file = file;
        
        try {
            Scanner reader = new Scanner(new File(file));
            while (reader.hasNextLine()) {
                String[] parts = reader.nextLine().split(";");
                int id = Integer.parseInt(parts[0]);
                int projectId = Integer.parseInt(parts[1]);
//                Date date = parse(parts[2]);
                int time = Integer.parseInt(parts[2]);
                int type = Integer.parseInt(parts[3]);
                String description = parts[4];
                String username = parts[5];
                
                
                Timecard timecard = new Timecard(id, projectId, time, type, description, username); //Timecards content from file
                timecards.add(timecard);
            }
        } catch (Exception e) {
            //System.out.println("file:  " +file);
            FileWriter writer = new FileWriter(new File(file));
            writer.close();
        }        
    }
    
    private void save() throws Exception {
        try (FileWriter writer = new FileWriter(new File(file))) {
            for (Timecard timecard : timecards) {
                writer.write(timecard.getId() + ";" + timecard.getProjectId() +
                        ";" + timecard.getTime() + ";" + timecard.getType() + ";" + timecard.getDescription() + ";" + timecard.getUsername() + "\n"); //Timecards content to file
            }
        }
    }    
    
    private int generateId() {
        return timecards.size() + 1;
    }
    
    @Override
    public List<Timecard> getAll() {
        return timecards;
    }
    
    @Override
    public Timecard add(Timecard timecard) throws Exception {
        timecard.setId(generateId());
        timecards.add(timecard);
        save();
        return timecard;
    }
}
