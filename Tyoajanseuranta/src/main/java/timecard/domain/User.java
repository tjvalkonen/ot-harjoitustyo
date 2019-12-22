package timecard.domain;

/**
 * Käyttäjää edustava luokka 
 */

public class User {
    
    private String name;
    private String username;

    public User(String username, String name) {
        this.name = name;
        this.username = username;
    }
    /**
    * Käyttäjätunnukseen liitetyn nimen hakeminen
    */
    
    public String getName() {
        return name;
    }
    
    /**
    * Käyttäjätunnuksen hakeminen
    */
    
    public String getUsername() {
        return username;
    }
    
    /**
    * Käyttäjätunnuksen vertaaminen toiseen
    */
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User)) {
            return false;
        }       
        User other = (User) obj;
        return username.equals(other.username);
    } 
    
}
