package timecard.domain;
/**
 * Yksittäistä projektia kuvaava luokka 
 */

public class Project {

    private int id;
    private String name;

    public Project(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public Project(String name) {
        this.name = name;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Project)) {
            return false;
        }
        Project other = (Project) obj;
        return id == other.id;
    }
}