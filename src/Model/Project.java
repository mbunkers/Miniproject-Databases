/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author Marc Bunkers, Fabian Claasen
 */
public class Project {
    
    private String naam;
    private int id;
    private ArrayList<Actie> actions;
    
    public Project(int id, String naam){
        this.naam = naam;
        this.id = id;
        actions = new ArrayList<Actie>();
    }
    
    public Project(){
        
    }

    public void addAction(int id, String description, String notes, int status_id, int context_id, int project_id, String action_date, String statuschange_date, boolean done)
    {
        actions.add(new Actie(id, description, notes, status_id, context_id, project_id, action_date, statuschange_date, done));
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

    public ArrayList<Actie> getActions() {
        return actions;
    }

    public void setActions(ArrayList<Actie> actions) {
        this.actions = actions;
    }
    
    
}
