/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import static Controller.Main.controller;
import Controller.DataLayer;
import Model.Actie;
import Model.Gedachte;
import Model.Project;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.*;
/**
 *
 * @author Marc Bunkers
 */
public class Projectscreen extends JPanel {

    private JList list;
    private Project project;
    private int huidigeSelectie = 0;
    private JScrollPane scroll;
    private JLabel projectNaam;

    public Projectscreen(String naam, int id) {
        projectNaam = new JLabel(naam);
        project = new Project(id, naam);
        init();
    }

    public Projectscreen(String naam) {
        projectNaam = new JLabel(naam);
        init();
    }

    public void init() {
        setLayout(new BorderLayout());
        list = new JList();

        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 1) {
                    int index = list.getSelectedIndex();
                    huidigeSelectie = index;
                }

                if (evt.getClickCount() == 2) {
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
            }
        });

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
        huidigeSelectie = -1;
        validate();
    }

    public void updateProjects() {
        ArrayList projects = DataLayer.getActions(getID());
        if (!projects.isEmpty()) {
            DefaultListModel model = new DefaultListModel();
            for (Object test : projects) {
                String weergave = (String) test;
                String[] actie = weergave.split(", ");

                int id;
                String description = "";
                String notes = "";
                int status_id;
                int context_id;
                int project_id;
                String action_date = "";
                String statuschange_date = "";
                boolean done = false;

                // check
                id = Integer.parseInt(actie[0]);
                description = actie[1];
                if (actie[2] != null) {
                    notes = actie[2];
                }
                status_id = Integer.parseInt(actie[3]);
                context_id = Integer.parseInt(actie[4]);
                project_id = Integer.parseInt(actie[5]);
                action_date = actie[6];
                statuschange_date = actie[7];
                if (actie[8].equals("true")) {
                    done = true;
                }
                project.addAction(id, description, notes, status_id, context_id,
                        project_id, action_date, statuschange_date, done);

                model.addElement(description);
            }
            list = new JList();
            list.setModel(model);

            list.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    if (evt.getClickCount() == 2) {
                        int idProject = project.getId();
                        int idActie = huidigeSelectie;
                        final Actie actie = project.getActions().get(idActie);
                        final JDialog dialog = new JDialog();
                        dialog.setPreferredSize(new Dimension(500, 200));
                        dialog.setTitle("Bewerk actie");
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

                        for (Projectscreen s : arraylist) {
                            if (s.getID() == idProject) {
                                String naam = s.getNaam();
                                box.addItem(naam);
                                box.validate();
                            }
                        }

                        for (Projectscreen s : arraylist) {
                            if (s.getID() != idProject && !s.getNaam().equals("Gedachten")) {
                                String naam = s.getNaam();
                                box.addItem(naam);
                                box.validate();
                            }
                        }

                        projects.add(projectnaam, BorderLayout.WEST);
                        projects.add(box, BorderLayout.CENTER);
                        master.add(projects);

                        // actie
                        JPanel gedachten = new JPanel();
                        gedachten.setLayout(new BorderLayout());
                        JLabel n = new JLabel("Actie: ");
                        final JTextField n1 = new JTextField();
                        final int id = controller.getCurrentThought();
                        final String s = (String) list.getSelectedValue();
                        n1.setText(s);
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
                        //ArrayList<Actie> actie = project.getActions();
                        //Actie actie1 = actie.get(idActie);
                        b1.setText(actie.getNotes());
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
                        if (!co2.isEmpty()) {
                            int counter = 1;
                            String temp = "";
                            for (String t : co2) {
                                if (counter == actie.getContext_id()) {
                                    co1.addItem(t);
                                    temp = t;
                                    System.out.println("test");
                                }
                                counter++;
                            }

                            for (String t : co2) {
                                if (!t.equals(temp)) {
                                    co1.addItem(t);
                                }
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
                        int counter = 1;
                        String temp = "";
                        for (String t : arrayStatus) {
                            if (counter == actie.getStatus_id()) {
                                box1.addItem(t);
                                temp = t;
                            }
                            counter++;
                        }

                        for (String t : arrayStatus) {
                            if (!t.equals(temp)) {
                                box1.addItem(t.toString());
                                box1.validate();
                            }
                        }
                        status.add(st, BorderLayout.WEST);
                        status.add(box1, BorderLayout.CENTER);
                        master.add(status);

                        // datum
                        JPanel datum = new JPanel();
                        datum.setLayout(new BorderLayout());
                        JLabel d = new JLabel("Datum");
                        final JFormattedTextField d1 = new JFormattedTextField(new SimpleDateFormat("yyyy-mm-dd"));
                        d1.setText(actie.getAction_date());
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
                                for (String t : co2) {
                                    if (t.equals(co3)) {
                                        bestaat = true;
                                    }
                                }
                                if (!bestaat) {
                                    DataLayer.addContext(co3);
                                }

                                String context = co3;
                                String status = box1.getSelectedItem().toString();
                                String datum = d1.getText();

                                if (datum.equals("yyyy-mm-dd") | datum.equals("")) {
                                    JOptionPane.showMessageDialog(null, "Vul een datum in", "Error", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    String update = DataLayer.changeAction(actie.getId(), description, notes, context, status, project.getId(), datum, false);
                                    String[] upd = update.split(", ");

                                    String des = "";
                                    String note = "";
                                    int status_id;
                                    int context_id;
                                    int project_id;
                                    String action_date = "";
                                    String statuschange_date = "";
                                    boolean done = false;

                                    // check
                                    des = upd[1];
                                    if (upd[2] != null) {
                                        note = upd[2];
                                    }
                                    status_id = Integer.parseInt(upd[3]);
                                    context_id = Integer.parseInt(upd[4]);
                                    project_id = Integer.parseInt(upd[5]);
                                    action_date = upd[6];
                                    statuschange_date = upd[7];
                                    if (upd[8].equals("true")) {
                                        done = true;
                                    }

                                    actie.setDescription(des);
                                    actie.setNotes(note);
                                    actie.setStatus_id(status_id);
                                    actie.setContext_id(context_id);
                                    actie.setProject_id(project_id);
                                    actie.setProject_id(project_id);
                                    actie.setAction_date(action_date);
                                    actie.setDone(done);
                                    
                                    dialog.setVisible(false);
                                }
                            }
                        });

                        dialog.pack();
                        dialog.setLocationRelativeTo(null);
                        dialog.setVisible(true);
                        dialog.validate();

                    }

                    if (evt.getClickCount() == 1) {
                        int index = list.getSelectedIndex();
                        huidigeSelectie = index;
                    }
                }
            });
            scroll.getViewport().setView(list);
            validate();
        }
        huidigeSelectie = 0;
        validate();
    }

    public int getHuidigeSelectie() {
        return huidigeSelectie;
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
