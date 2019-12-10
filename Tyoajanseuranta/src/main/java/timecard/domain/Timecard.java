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
    private String username;

    public Timecard(int id, int projectId, int time, int type, String description, String username) { // Date date,
        this.id = id;
        this.projectId = projectId;
//        this.date = date;
        this.time = time;
        this.type = type;
        this.description = description;
        this.username = username;
    }
    
    public Timecard(int projectId, int time, int type, String description, String username) {
        this.projectId = projectId;
//        this.date = date;
        this.time = time;
        this.type = type;
        this.description = description;
        this.username = username;
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
    
    public int getTime() {
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
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getUsername(){
        return username;
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
