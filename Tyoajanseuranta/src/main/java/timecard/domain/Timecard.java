package timecard.domain;
/**
 * Yksittäistä työajanmerkintää kuvaava luokka 
 */

public class Timecard {
    
    private int id;
    private int projectId;
    private int time;
    private int type;
    private String description;
    private String username;

    public Timecard(int id, int projectId, int time, int type, String description, String username) { // Date date,
        this.id = id;
        this.projectId = projectId;
        this.time = time;
        this.type = type;
        this.description = description;
        this.username = username;
    }
    
    public Timecard(int projectId, int time, int type, String description, String username) {
        this.projectId = projectId;
        this.time = time;
        this.type = type;
        this.description = description;
        this.username = username;
    }
    
    /**
    * Työaikamerkinnän tunnuksen asettaminen
    *
    */ 
    
    public void setId(int id) {
        this.id = id;
    }
    
    /**
    * Työaikamerkinnän tunnuksen hakeminen
    *
    */ 
    
    public int getId() {
        return id;
    }
    
    /**
    * Työaikamerkinnän projektin tunnuksen asettamien
    *
    */
    
    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
    
    /**
    * Työaikamerkinnän projektin tunnuksen hakeminen
    *
    */
    
    public int getProjectId() {
        return projectId;
    }
    
    /**
    * Työaikamerkinnän työajan merkitseminen (minuutteina)
    *
    */   
    
    public void setTime(int time) {
        this.time = time;
    }
    
    /**
    * Työaikamerkinnän työajan hakeminen (minuutteina)
    *
    */
    
    public int getTime() {
        return time;
    }
    
    /**
    * Työaikamerkinnän työn tyypin asettaminen (kokonaisuluku 0-4)
    * 0 = Not Selected,
    * 1 = Design,
    * 2 = Programming,
    * 3 = Testing,
    * 4 = Maintenance,
    *
    */
    
    public void setType(int type) {
        this.type = type;
    }
    
    /**
    * Työaikamerkinnän työn tyypin hakeminen (kokonaisuluku 0-4)
    * 0 = Not Selected,
    * 1 = Design,
    * 2 = Programming,
    * 3 = Testing,
    * 4 = Maintenance,
    *
    */
    
    public int getType() {
        return type;
    }
    
    /**
    * Työaikamerkinnän kuvauksen asettaminen
    *
    */
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
    * Työaikamerkinnän kuvauksen hakeminen
    *
    */
    
    public String getDescription() {
        return description;
    }
    
    /**
    * Työaikamerkinnän työntekijän asettaminen (käyttäjätunnus)
    *
    */
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
    * Työaikamerkinnän työntekijän hakeminen (käyttäjätunnus)
    *
    */  
    
    public String getUsername() {
        return username;
    }
    
    /**
    * Työaikamerkinnän vertaaminen
    *
    */  
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Timecard)) {
            return false;
        }
        Timecard other = (Timecard) obj;
        return id == other.id;
    }
}
