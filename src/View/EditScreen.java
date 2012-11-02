/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.DataLayer;
import static Controller.Main.controller;
import Model.Actie;
import Model.Project;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author Marc Bunkers, Fabian Claasen
 */
public class EditScreen extends JPanel implements ActionListener{
    
    private JTextField projectNaam, actieNaam, actie, date;
    private JLabel project, actieNaamLabel, actieLabel, datum, prioriteit;
    private JList select;
    private JButton button;
    
    private JTextField gedachte;
    private JButton gedachteSave;

    private Actie action;
    private Project projectClass;
    
    public EditScreen(int id){
        if(id == 1){
            makeFields();
            
            setLayout(new GridLayout(3, 4));
            
            add(project);
            add(projectNaam);
            add(datum);
            add(date);
            add(actieNaamLabel);
            add(actieNaam);
            add(prioriteit);
            add(select);
            add(actieLabel);
            add(actie);
            add(button);
        }
        
        if(id == 2){
            makeFields2();
            
            setLayout(new GridLayout(2, 3));

            add(gedachte);
            add(gedachteSave);
        }
    }
    
    private void makeFields(){
        projectNaam = new JTextField();
        actieNaam = new JTextField();
        actie = new JTextField();
        date = new JTextField();
        select = new JList();
        
        project = new JLabel("Project");
        actieNaamLabel = new JLabel("actienaam");
        actieLabel = new JLabel("actie");
        datum = new JLabel("datum");
        prioriteit = new JLabel("prioriteit");
        
        button = new JButton("Save");
        button.addActionListener(this);
    }
    
    private void makeFields2(){
        gedachte = new JTextField();
        
        gedachteSave = new JButton("Save!");
        gedachteSave.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Save")){
            //projectClass.putProject(projectNaam.getText());
            //action.putActie(actieNaam.getText(), actie.getText(), date.getText());
        }
        
        if(e.getActionCommand().equals("Save!")){
            DataLayer.putGedachte(gedachte.getText());
            controller.updateThouhts();
        }
    }
}
