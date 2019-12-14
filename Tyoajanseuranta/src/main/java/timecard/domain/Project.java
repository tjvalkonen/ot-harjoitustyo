package timecard.domain;
/**
 * Yksittäistä projektia kuvaava luokka 
 */

public class Project {

    private int id;
    private String name;
    private int etc;

    public Project(int id, String name, int etc) {
        this.id = id;
        this.name = name;
        this.etc = etc;
    }
    
    public Project(String name, int etc) {
        this.name = name;
        this.etc = etc;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setEtc(int time) {
        this.etc = etc;
    }
    
    public int getEtc() {
        return etc;
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