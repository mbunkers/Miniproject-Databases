/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Marc Bunkers, Fabian Claasen
 */

public class Actie {
    private int id;
    private String description;
    private String notes;
    private int status_id;
    private int context_id;
    private int project_id;
    private String action_date;
    private String statuschange_date;
    private boolean done;
    
    public Actie(){
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public int getContext_id() {
        return context_id;
    }

    public void setContext_id(int context_id) {
        this.context_id = context_id;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public String getAction_date() {
        return action_date;
    }

    public void setAction_date(String action_date) {
        this.action_date = action_date;
    }

    public String getStatuschange_date() {
        return statuschange_date;
    }

    public void setStatuschange_date(String statuschange_date) {
        this.statuschange_date = statuschange_date;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
    
    
}
