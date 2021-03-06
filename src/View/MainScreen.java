/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.DataLayer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author Marc Bunkers, Fabian Claasen
 */
public class MainScreen extends JFrame {

    private JScrollBar scroll;
    private JPanel gedachten, projecten;
    private ArrayList projects;
    private ArrayList projectScreen;
    private Menubalk menu;
    private boolean ingelogd;
    private int height = 520;
    private int width = 780;
    private int p1 = 0, p2 = 1, p3 = 2;

    public MainScreen() {
        setTitle("MiniProject Databases & Java");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        menu = new Menubalk();
        setJMenuBar(menu);
        setPreferredSize(new Dimension(800, 600));
        pack();

        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        
        ingelogd = false;
        startConnectie();
        init();
    }

    public void init() {
        projects = new ArrayList<Projectscreen>();
        createPanels();
    }

    private void createPanels() {
        setLayout(new BorderLayout());

        // Gedachten scherm
        projectScreen = new ArrayList<Projectscreen>();
        gedachten = new JPanel();
        add(gedachten, BorderLayout.CENTER);
        //gedachten.setBounds(0, 0, 800, 600);
        gedachten.setPreferredSize(new Dimension(width, height));
        gedachten.setBackground(Color.BLUE);
        gedachten.setLayout(new GridLayout(1, 3));

        projecten = new JPanel(new BorderLayout());
        projecten.add(gedachten, BorderLayout.CENTER);
        add(projecten);

        // schermen daarin
        gedachten.add(addProject(1));
        scroll = new JScrollBar(JScrollBar.HORIZONTAL);
        scroll.setEnabled(false);
        scroll.setMaximum(0);
        scroll.setVisibleAmount(1);
        ArrayList arraylist = DataLayer.getProjects();

        for (Object test : arraylist) {
            String p = (String) test;
            String[] split = p.split(" ");

            Projectscreen project = addProject((Integer.parseInt(split[0])), split[1]);

            projects.add(1, project);
            if (projects.size() < 4) {
                gedachten.add(project);
            }
            projectScreen.add(project);
        }

        if (projects.size() == 2) {
            gedachten.add(addProject(2));
        }

        if (projects.size() == 1) {
            gedachten.add(addProject(2));
            gedachten.add(addProject(2));
        }



        scroll.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent ae) {
                if (ae.getValueIsAdjusting()) {
                    gedachten.remove(0);
                    //gedachten.remove(1);
                    //gedachten.remove(2);
                    int index = ae.getValue();

                    int counter = 0;
                    while (counter < 3) {
                        Projectscreen project = (Projectscreen) projectScreen.get(index + counter);
                        gedachten.add(project);
                        
                        if (counter == 0){
                            p1 = project.getID();
                        }
                        else {
                            if (counter == 1){
                                p2 = project.getID();
                            }
                            else {
                                p3 = project.getID();
                            }
                        }
                        counter++;
                    }
                    validate();
                }
            }
        });

        add(scroll);
        add(projecten);
        projecten.add(scroll, BorderLayout.SOUTH);
        validate();
    }

    public void updateThoughts() {
        Projectscreen project = (Projectscreen) projects.get(0);
        project.updateThoughts();
    }

    public void updateProjects(String newProject) {
        String[] e = newProject.split(" ");

        int id = Integer.parseInt(e[0]);
        String naam = e[1];

        Projectscreen project = addProject(id, naam);
        projects.add(1, project);
        projectScreen.add(1, project);

        gedachten.remove(0);

        int counter = 0;
        while (counter < 3) {
            Projectscreen projectx = (Projectscreen) projectScreen.get(counter);
            gedachten.add(projectx);
            counter++;
        }
        gedachten.validate();
        validate();
    }

    public void updateScreen() {
        validate();
    }

    private Projectscreen addProject(int id, String name) {
        Projectscreen project = new Projectscreen(name, id);
        //projects.add(project);
        if (projects.size() > 4) {
            scroll.setEnabled(true);
            scroll.setMaximum(scroll.getMaximum() + 1);
        }
        project.updateProjects();
        return project;
    }

    private Projectscreen addProject(int i) {
        Projectscreen project = null;
        if (i == 1) {
            project = new Projectscreen("Gedachten", 0);
            project.updateThoughts();
            projects.add(project);
            projectScreen.add(project);
        }

        if (i == 2) {
            project = new Projectscreen("Untitled Project");
        }
        return project;
    }

    public void startConnectie() {
        final JDialog inlog = new JDialog(this, "Inloggen", true);
        inlog.setBounds(0, 0, 200, 150);

        JPanel panel = new JPanel();
        JPanel labels = new JPanel();
        JPanel txtfields = new JPanel();
        JButton commit = new JButton("Log in");

        panel.setLayout(new BorderLayout());
        labels.setLayout(new GridLayout(2, 0));
        txtfields.setLayout(new GridLayout(2, 0));

        final JLabel name = new JLabel("Username");
        final JLabel pssword = new JLabel("Password");
        final JTextField username = new JTextField("miniproject7"); // voor ontwikkel en test doeleinde is dit ingevuld
        final JPasswordField password = new JPasswordField("Pp3jWFQH"); // voor ontwikkel en test doeleinde is dit ingevuld

        inlog.add(panel);
        panel.add(labels, BorderLayout.WEST);
        panel.add(txtfields, BorderLayout.CENTER);
        panel.add(commit, BorderLayout.SOUTH);

        labels.add(name);
        labels.add(pssword);
        txtfields.add(username, BorderLayout.CENTER);
        txtfields.add(password, BorderLayout.CENTER);
        username.setBounds(50, 10, 200, 20);
        password.setBounds(50, 10, 200, 20);
        commit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                if (!DataLayer.init(username.getText(), password.getText())) {
                    ingelogd = false;
                    username.setText("");
                    name.setForeground(Color.RED);
                    password.setText("");
                    pssword.setForeground(Color.RED);
                } else {
                    ingelogd = true;
                    inlog.setVisible(false);
                }
            }
        });

        inlog.pack();
        inlog.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        inlog.setLocationRelativeTo(null);
        inlog.setResizable(false);
        inlog.setVisible(true);
    }

    public void logOff() {
        ingelogd = false;
        startConnectie();
    }

    public void quit() {
        ingelogd = false;
        System.exit(0);
    }

    public void updateMain() {
        updateScreen();
    }

    public int getCurrentThought() {
        Projectscreen project = (Projectscreen) projects.get(0);
        return project.getHuidigeSelectie();
    }

    public ArrayList getProjects() {
        return projects;
    }

    public ArrayList getProjectScreen() {
        return projectScreen;
    }
    
    public void updateIDprojects(int id)
    {
        projects.remove(id);
        int aantal = projects.size();
        while (id < aantal){
            Projectscreen project = (Projectscreen) projects.get(id);
            project.setID(id);
            id++;
        }
        
        scroll.setMaximum(scroll.getMaximum() - 1);
        
        gedachten.remove(0);
        int counter = 1;
        while (counter < 4){
            int index;
            if (counter == 1){
                index = p1;
            }
            else {
                if (counter == 2){
                    index = p2;
                }
                else {
                    index = p3;
                }
            }
            Projectscreen project = (Projectscreen) projects.get(index);
            gedachten.add(project);
            counter++;
        }
        gedachten.validate();
        validate();
    }

    public void updateProjects() {
        int counter = 1;
        for (Object p : projects){
            Projectscreen project = (Projectscreen) p;
            if (counter == 1){
                counter++;
            }
            else {
            project.updateProjects();
            }
        }
    }
}
