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
    
    /**
    * Projektin tunnuksen asettaminen
    *
    */
    
    public void setId(int id) {
        this.id = id;
    }

    /**
    * Projektin tunnuksen hakeminen
    *
    */
    
    public int getId() {
        return id;
    }
    
    /**
    * Projektin nimen hakeminen
    *
    */
    
    public String getName() {
        return name;
    }

    /**
    * Projektin arvioidun työajan asettaminen (minuutteina)
    *
    */
    
    public void setEtc(int time) {
        this.etc = etc;
    }
    
    /**
    * Projektin arvioidun työajan hakeminen (minuutteina)
    *
    */
    
    public int getEtc() {
        return etc;
    }
    
    /**
    * Projektin arvioidun työajan hakeminen tekstimuodossa
    *
    */
    
    public String getEtcString() {
        int hours = etc / 60;
        int minutes = etc % 60;
        String timeString = Integer.toString(hours) + "h " + Integer.toString(minutes) + "min";
        
        return timeString;
    }
    
    /**
    * Projektin vertaaminen toiseen
    *
    */
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Project)) {
            return false;
        }
        Project other = (Project) obj;
        return id == other.id;
    }
}