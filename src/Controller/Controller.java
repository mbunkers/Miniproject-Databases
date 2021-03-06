/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.MainScreen;
import View.Projectscreen;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Marc Bunkers
 */
public class Controller {
    
    private MainScreen mainscreen;

    public Controller() {
        mainscreen = new MainScreen();
    }
    
    public void logOff()
    {
        mainscreen.logOff();
    }
    
    public void quit()
    {
        mainscreen.quit();
    }
    
    public void add(JPanel panel){
        mainscreen.removeAll();
        mainscreen.validate();
        mainscreen.add(panel);
        mainscreen.validate();
    }
    
    public void updateMain(){
        mainscreen.updateMain();
    }

    public void updateThouhts() {
        mainscreen.updateThoughts();
    }
    
    public int getCurrentThought()
    {
        return mainscreen.getCurrentThought();
    }

    public void updateProjects(String newProject) {
        mainscreen.updateProjects(newProject);
    }
    
    public ArrayList getProjects()
    {
        ArrayList<Projectscreen> arraylist = mainscreen.getProjectScreen();
        return arraylist;
    }
    
    public void updateIDprojects(int id)
    {
        mainscreen.updateIDprojects(id);
    }

    public void updateProjects() {
        mainscreen.updateProjects();
    }
}
