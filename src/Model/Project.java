/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
/**
 *
 * @author Marc Bunkers, Fabian Claasen
 */
public class Project {
    
    private String naam;
    private int id;
    
    public Project(int id, String naam){
        this.naam = naam;
        this.id = id;
    }
    
    public Project(){
        
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
