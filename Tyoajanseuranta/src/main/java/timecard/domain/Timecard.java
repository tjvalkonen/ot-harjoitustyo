package timecard.domain;

/**
 * Yksittäistä työajanmerkintää kuvaava luokka 
 */

import java.util.Date;

public class Timecard {
    
    private int id;
    private int projectId;
//    private Date date;
    private int time;
    private int type;
    private String description;

    public Timecard(int id, int projectId, int time, int type, String description) { // Date date,
        this.id = id;
        this.projectId = projectId;
//        this.date = date;
        this.time = time;
        this.type = type;
        this.description = description;
    }
    
    public Timecard(int projectId, int time, int type, String description) {
        this.projectId = projectId;
//        this.date = date;
        this.time = time;
        this.type = type;
        this.description = description;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    
    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
    
    public int getProjectId() {
        return projectId;
    }
    
//    public void setDate(Date date) {
//        this.date = date;
//    }
//    
//    public Date getDate() {
//        return date;
//    }
    
    public void setTime(int time) {
        this.time = time;
    }    
    
    public int getTime(){
        return time;
    }
    
    public void setType(int type) {
        this.type = type;
    }
    
    public int getType() {
        return type;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Timecard)) {
            return false;
        }
        Timecard other = (Timecard) obj;
        return id == other.id;
    }
}
