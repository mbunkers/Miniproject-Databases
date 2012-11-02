/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.DataLayer;
import Model.Actie;
import Model.Gedachte;
import Model.Project;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author Marc Bunkers
 */
public class Projectscreen extends JPanel {

    private JList list;
    private Project project;
    private ArrayList<Actie> actions;
    private JScrollPane scroll;
    private JLabel projectNaam;

    public Projectscreen(String naam, int id) {
        projectNaam = new JLabel(naam);
        project = new Project(id, naam);
        actions = new ArrayList<Actie>();
        init();
    }

    public Projectscreen(String naam) {
        projectNaam = new JLabel(naam);
        init();
    }

    public void init() {
        setLayout(new BorderLayout());
        list = new JList();
        add(list, BorderLayout.CENTER);
        setBorder(BorderFactory.createBevelBorder(5, Color.BLACK, Color.BLACK));
        setBackground(Color.yellow);
        add(projectNaam, BorderLayout.NORTH);

        scroll = new JScrollPane();
        add(scroll, BorderLayout.CENTER);
        validate();
    }

    public void changeName(String naam) {
        projectNaam.setText(naam);
        project.setNaam(naam);
    }

    public void updateThoughts() {
        ArrayList thoughts = DataLayer.getAllThoughts();
        DefaultListModel model = new DefaultListModel();
        for (Object test : thoughts) {
            Gedachte t = (Gedachte) test;
            String weergave = t.getId() + ". " + t.getNotes();
            model.addElement(weergave);
        }
        list.setModel(model);
        scroll.getViewport().setView(list);
        validate();
    }

    public void updateProjects() {
        ArrayList projects = DataLayer.getActions(getID());
        if (!projects.isEmpty()) {
            DefaultListModel model = new DefaultListModel();
            for (Object test : projects) {
                String weergave = (String) test;
                model.addElement(weergave);
            }
            list = new JList();
            list.setModel(model);

            list.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    if (evt.getClickCount() == 2) {
                        int index = list.getSelectedIndex();
                        
                    } 
                }
            });
            scroll.getViewport().setView(list);
            validate();
        }
        validate();
    }

    public int getHuidigeSelectie() {
        return list.getSelectedIndex();
    }

    public int getID() {
        return project.getId();
    }

    public void setID(int i) {
        project.setId(i);
    }

    public String getNaam() {
        return project.getNaam();
    }

    public JList getList() {
        return list;
    }
}
