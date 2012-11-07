/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.DataLayer;
import static Controller.Main.controller;
import Model.Databaseconnector;
import Model.Gedachte;
import Model.Project;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author Marc Bunkers, Fabian Claasen
 */
public class Menubalk extends JMenuBar implements ActionListener, KeyListener {

    private JMenuItem afsluiten, acties, toevoegen, uitloggen, editMenu, kalenderOverzicht,
            wisGedachte, editGedachte, addProject, deleteProject, editProject, omzetten;
    private JMenu file, edit, gebruiker, kalender, gedachte, project;
    private JTextField zoekBalk;
    Project pj = new Project();
    private Databaseconnector dc = new Databaseconnector();

    public Menubalk() {
        makeItems();
        add(file);
        file.add(afsluiten);

        add(edit);
        ///edit.add(editMenu);
        //edit.add(acties);

        edit.add(gedachte);
        gedachte.add(toevoegen);
        gedachte.add(omzetten);
        gedachte.add(editGedachte);
        gedachte.add(wisGedachte);

        edit.add(project);
        project.add(addProject);
        project.add(editProject);
        project.add(deleteProject);

        add(kalender);
        kalender.add(kalenderOverzicht);

        add(gebruiker);
        gebruiker.add(uitloggen);

        add(zoekBalk);
    }

    private void makeItems() {
        zoekBalk = new JTextField();
        zoekBalk.addKeyListener(this);

        file = new JMenu("File");
        afsluiten = new JMenuItem("Afsluiten");
        afsluiten.addActionListener(this);

        gedachte = new JMenu("Gedachte");
        editGedachte = new JMenuItem("Edit gedachte");
        editGedachte.addActionListener(this);
        omzetten = new JMenuItem("Gedachte omzetten naar actie");
        omzetten.addActionListener(this);
        wisGedachte = new JMenuItem("Wis gedachte");
        wisGedachte.addActionListener(this);

        project = new JMenu("Project");
        addProject = new JMenuItem("Voeg project toe");
        addProject.addActionListener(this);
        editProject = new JMenuItem("Bewerk project");
        editProject.addActionListener(this);
        deleteProject = new JMenuItem("Verwijder project");
        deleteProject.addActionListener(this);

        acties = new JMenuItem("Acties");
        acties.addActionListener(this);
        edit = new JMenu("Edit");
        editMenu = new JMenuItem("Editmenu");
        editMenu.addActionListener(this);
        kalender = new JMenu("Kalender");
        kalenderOverzicht = new JMenuItem("Kalenderoverzicht");
        toevoegen = new JMenuItem("Gedachte toevoegen");
        toevoegen.addActionListener(this);
        gebruiker = new JMenu("Gebruiker");
        uitloggen = new JMenuItem("Uitloggen");
        uitloggen.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Acties")) {
            controller.add(new Actiescreen());
        }

        if (e.getActionCommand().equals("Editmenu")) {
            JDialog dialog = new JDialog();
            dialog.setBounds(0, 0, 300, 500);

            dialog.add(new EditScreen(1));
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        }

        if (e.getActionCommand().equals("Kalenderoverzicht")) {
            controller.add(new Kalenderscreen());
        }

        if (e.getActionCommand().equals("Gedachte toevoegen")) {
            JDialog dialog = new JDialog();
            dialog.setTitle("Gedachte Toevoegen");
            dialog.setModal(true);
            dialog.setBounds(0, 0, 300, 500);

            dialog.add(new EditScreen(2));
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
            dialog.validate();
        }

        if (e.getActionCommand().equals("Edit gedachte")) {
            final JDialog dialog = new JDialog();
            dialog.setTitle("Edit gedachte");
            dialog.setModal(true);
            dialog.setBounds(0, 0, 300, 500);

            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            final JTextField text = new JTextField();
            JButton button = new JButton("Toepassen");
            panel.add(text, BorderLayout.NORTH);
            panel.add(button, BorderLayout.SOUTH);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    String change = text.getText();
                    int gedachte = controller.getCurrentThought();
                    DataLayer.changeThought(gedachte, change);
                    dialog.setVisible(false);
                    controller.updateThouhts();
                }
            });

            dialog.add(panel);
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
            dialog.validate();
        }

        if (e.getActionCommand().equals("Gedachte omzetten naar actie")) {
            ArrayList<Projectscreen> test = controller.getProjects();
            if (test.get(0).getList().getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Selecteer eerst een gedachte", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                final JDialog dialog = new JDialog();
                dialog.setPreferredSize(new Dimension(500, 200));
                dialog.setTitle("Gedachte omzetten naar actie");
                dialog.setModal(true);

                JPanel master = new JPanel();
                dialog.add(master);
                master.setLayout(new GridLayout(0, 1));

                // Project kiezen
                JPanel projects = new JPanel();
                projects.setLayout(new BorderLayout());
                JLabel projectnaam = new JLabel("Project");
                final JComboBox box = new JComboBox();
                final ArrayList<Projectscreen> arraylist = controller.getProjects();
                Projectscreen thoughts = arraylist.get(0);
                for (Projectscreen s : arraylist) {
                    if (!s.getNaam().equals("Gedachten")){
                    String naam = s.getNaam();
                    box.addItem(naam);
                    box.validate();
                    }
                }
                projects.add(projectnaam, BorderLayout.WEST);
                projects.add(box, BorderLayout.CENTER);
                master.add(projects);

                //gedachte
                JPanel gedachten = new JPanel();
                gedachten.setLayout(new BorderLayout());
                JLabel n = new JLabel("Gedachte: ");
                final JTextField n1 = new JTextField();
                final int id = controller.getCurrentThought();
                final JList list = thoughts.getList();
                final String s = (String) list.getSelectedValue();
                String[] s1 = s.split(" ");
                String s2 = s1[0];
                n1.setText(s.replaceFirst(s2, ""));
                n1.setText(n1.getText().trim());
                gedachten.add(n, BorderLayout.WEST);
                gedachten.add(n1, BorderLayout.CENTER);
                master.add(gedachten);

                // beschrijving
                JPanel beschrijving = new JPanel();
                beschrijving.setLayout(new BorderLayout());
                JLabel b = new JLabel("Beschrijving");
                final JTextArea b1 = new JTextArea(0, 3);
                b1.setLineWrap(true);
                b1.setRows(3);
                b1.setWrapStyleWord(true);
                b1.setColumns(0);
                JScrollPane scroll = new JScrollPane();
                scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                scroll.getViewport().setView(b1);
                beschrijving.add(b, BorderLayout.WEST);
                beschrijving.add(scroll, BorderLayout.CENTER);
                master.add(beschrijving);
                
                // context
                
                JPanel context = new JPanel();
                context.setLayout(new BorderLayout());
                JLabel co = new JLabel("Context");
                final JComboBox co1 = new JComboBox();
                co1.setEditable(true);
                final ArrayList<String> co2 = DataLayer.getContexts();
                if (!co2.isEmpty()){
                    for (String t : co2){
                        co1.addItem(t);
                    }
                }
                
                context.add(co, BorderLayout.WEST);
                context.add(co1, BorderLayout.CENTER);
                master.add(context);

                // status
                JPanel status = new JPanel();
                status.setLayout(new BorderLayout());
                JLabel st = new JLabel("Status");
                final JComboBox box1 = new JComboBox();
                ArrayList<String> arrayStatus = DataLayer.getStatuses();
                for (String t : arrayStatus) {
                    box1.addItem(t.toString());
                    box1.validate();
                }
                status.add(st, BorderLayout.WEST);
                status.add(box1, BorderLayout.CENTER);
                master.add(status);

                // datum
                JPanel datum = new JPanel();
                datum.setLayout(new BorderLayout());
                JLabel d = new JLabel("Datum");
                final JFormattedTextField d1 = new JFormattedTextField(new SimpleDateFormat("yyyy-mm-dd"));
                d1.setText("yyyy-mm-dd");
                datum.add(d, BorderLayout.WEST);
                datum.add(d1, BorderLayout.CENTER);
                master.add(datum);

                // button
                JButton toepassen = new JButton("Toepassen");
                master.add(toepassen);
                toepassen.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        String description = n1.getText();
                        String notes = b1.getText();
                        
                        String co3 = (String) co1.getSelectedItem();
                        boolean bestaat = false;
                        for (String t : co2){
                            if (t.equals(co3)){
                                bestaat = true;
                            }
                        }
                        
                        if (!bestaat){
                            DataLayer.addContext(co3);
                        }
                        
                        String context = co3;
                        String status = box1.getSelectedItem().toString();
                        int projectid = arraylist.get(box.getSelectedIndex()).getID() + 1;
                        String datum = d1.getText();
                        int idGedachte = Integer.parseInt("" + s.charAt(0)) - 1;

                        if (datum.equals("yyyy-mm-dd") | datum.equals("")) {
                            JOptionPane.showMessageDialog(null, "Vul een datum in", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            DataLayer.addAction(description, notes, context, status, projectid, datum);
                            DataLayer.deleteThought(idGedachte);
                            controller.updateThouhts();
                            controller.updateProjects();
                            dialog.setVisible(false);
                            ArrayList<Projectscreen> project = controller.getProjects();
                            Projectscreen t = project.get(0);
                            int i = t.getList().getModel().getSize();
                            int x = t.getList().getSelectedIndex();
                        }
                    }
                });

                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
                dialog.validate();
            }
        }

        if (e.getActionCommand().equals("Wis gedachte")) {
            DataLayer.deleteThought(controller.getCurrentThought());
            controller.updateThouhts();
        }

        if (e.getActionCommand().equals("Voeg project toe")) {
            final JDialog dialog = new JDialog();
            dialog.setTitle("Project toevoegen");
            dialog.setModal(true);

            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            final JTextField naam = new JTextField();

            JPanel projectnaam = new JPanel();
            projectnaam.setLayout(new BorderLayout());
            projectnaam.add(new JLabel("Projectnaam"), BorderLayout.WEST);
            projectnaam.add(naam, BorderLayout.CENTER);

            JButton button = new JButton("Aanmaken");
            panel.add(projectnaam, BorderLayout.NORTH);
            panel.add(button, BorderLayout.SOUTH);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    String e = DataLayer.addProject(naam.getText());
                    controller.updateProjects(e);
                    dialog.setVisible(false);
                    validate();
                }
            });

            dialog.add(panel);
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
            dialog.validate();
        }

        if (e.getActionCommand().equals("Bewerk project")) {
            final JDialog dialog = new JDialog();
            dialog.setTitle("Bewerk project");
            dialog.setModal(true);
            dialog.setBounds(0, 0, 300, 500);

            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            final JTextField naam = new JTextField();
            final JComboBox box = new JComboBox();
            box.setEditable(false);
            final ArrayList<Projectscreen> projects = controller.getProjects();
            projects.remove(0);
            for (Projectscreen t : projects) {
                box.addItem(t.getNaam());
                box.validate();
            }

            JPanel projectid = new JPanel();
            projectid.setLayout(new BorderLayout());
            projectid.add(new JLabel("Kies project"), BorderLayout.WEST);
            projectid.add(box, BorderLayout.CENTER);

            JPanel projectnaam = new JPanel();
            projectnaam.setLayout(new BorderLayout());
            projectnaam.add(new JLabel("Nieuwe projectnaam"), BorderLayout.WEST);
            projectnaam.add(naam, BorderLayout.CENTER);

            JButton button = new JButton("Bewerk");
            panel.add(projectid, BorderLayout.NORTH);
            panel.add(projectnaam, BorderLayout.CENTER);
            panel.add(button, BorderLayout.SOUTH);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    int d = box.getSelectedIndex();
                    projects.get(d).changeName(naam.getText());
                    DataLayer.changeProject(projects.get(d).getID(), naam.getText());
                    dialog.setVisible(false);
                    validate();
                }
            });

            dialog.add(panel);
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
            dialog.validate();
        }

        if (e.getActionCommand().equals("Verwijder project")) {
            final JDialog dialog = new JDialog();
            dialog.setTitle("Wis project");
            dialog.setModal(true);
            dialog.setBounds(0, 0, 300, 500);

            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            final JTextField naam = new JTextField();

            final JComboBox box = new JComboBox();
            box.setEditable(false);
            final ArrayList<Projectscreen> projects = controller.getProjects();
            projects.remove(0);
            for (Projectscreen t : projects) {
                box.addItem(t.getNaam());
                box.validate();
            }

            JPanel projectnaam = new JPanel();
            projectnaam.setLayout(new BorderLayout());
            projectnaam.add(new JLabel("Project"), BorderLayout.WEST);
            projectnaam.add(box, BorderLayout.CENTER);

            JButton button = new JButton("Wissen");
            panel.add(projectnaam, BorderLayout.NORTH);
            panel.add(button, BorderLayout.SOUTH);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    //pj.deleteProject(naam.getText(), id.getNumber().intValue()); 
                    int d = box.getSelectedIndex();
                    projects.get(d).changeName(naam.getText());
                    DataLayer.deleteProject(projects.get(d).getID());
                    controller.updateIDprojects(d);
                    dialog.setVisible(false);
                    validate();
                }
            });

            dialog.add(panel);
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
            dialog.validate();
        }

        if (e.getActionCommand().equals("Uitloggen")) {
            controller.logOff();
        }

        if (e.getActionCommand().equals("Afsluiten")) {
            controller.quit();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

         if(keyCode == KeyEvent.VK_ENTER){
            final JDialog dialog = new JDialog();
            dialog.setTitle("Search results");
            dialog.setModal(true);
            int height = 40;
            
            dialog.setBounds(0, 0, 300, 500);

            JPanel panel = new JPanel();                     
            ArrayList list = DataLayer.search(zoekBalk.getText());
            panel.setLayout(new GridLayout(list.size(), 1));
            
            if(list.size() == 0){
                JOptionPane.showMessageDialog(null, zoekBalk.getText() + " kon niet gevonden worden/ bestaat niet!", "Niet gevonden", JOptionPane.INFORMATION_MESSAGE);
                //panel.add(new JLabel(zoekBalk.getText() + " kon niet gevonden worden/ bestaat niet!"));
            }else{
                for(int i = 0; i < list.size(); i++){
                    panel.add(new JLabel(list.get(i).toString()));
                    height = height + 20;
                }
                dialog.setPreferredSize(new Dimension(200, height));
                dialog.add(panel);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
                dialog.validate();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //
    }
}
