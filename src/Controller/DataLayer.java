/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Gedachte;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marc Bunkers, Fabian Claasen
 */
public class DataLayer {

    private DataLayer() {
    }

    public static boolean init(String username, String password) {
        Database.init("databases.aii.avans.nl", "miniproject7_db2", username, password);
        Connection con = Database.getConnection();
        return (con != null);
    }

    public static int aantalID(String table) {
        try {
            java.sql.Connection con = Database.getConnection();
            java.sql.Statement s = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ResultSet idTest;
            idTest = s.executeQuery("select count(id) as aantalID from " + table);
            idTest.first();
            int id = idTest.getInt("aantalID");
            if (id == 0) {
                id = 1;
            } else {
                id = id + 1;
            }

            return id;
        } catch (SQLException ex) {
            Logger.getLogger(DataLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public static void putGedachte(String notes) {
        try {
            java.sql.Connection con = Database.getConnection();
            java.sql.Statement s = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            int id = aantalID("thoughts");

            ResultSet thoughtInvoegen;
            thoughtInvoegen = s.executeQuery("select * from thoughts");
            thoughtInvoegen.moveToInsertRow();
            thoughtInvoegen.updateInt("id", id);
            thoughtInvoegen.updateString("notes", notes);
            thoughtInvoegen.insertRow();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static ArrayList getAllThoughts() {
        try {
            ArrayList arrayList = new ArrayList<Gedachte>();

            java.sql.Connection con = Database.getConnection();
            java.sql.Statement s = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ResultSet thoughts;
            thoughts = s.executeQuery("select * from thoughts");
            if (thoughts.next()) {
                thoughts.first();

                int id1 = thoughts.getInt("id");
                String notes1 = thoughts.getString("notes");
                Gedachte gedachte1 = new Gedachte(id1, notes1);
                arrayList.add(gedachte1);

                while (thoughts.next()) {
                    int id = thoughts.getInt("id");
                    String notes = thoughts.getString("notes");

                    Gedachte gedachte = new Gedachte(id, notes);
                    arrayList.add(gedachte);
                }
            }
            return arrayList;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void deleteThought(int index) {
        try {
            java.sql.Connection con = Database.getConnection();
            java.sql.Statement s = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            int indext = index + 1;
            ResultSet thoughts;
            thoughts = s.executeQuery("select * from thoughts");
            thoughts.absolute(indext);
            thoughts.deleteRow();

            int size = aantalID("thoughts");
            ResultSet thoughts2;
            thoughts2 = s.executeQuery("select * from thoughts");
            if (thoughts2.next()) {
                while (indext < size) {
                    thoughts2.absolute(indext);
                    thoughts2.updateInt("id", indext);
                    thoughts2.updateRow();
                    indext++;
                    thoughts2.next();
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(DataLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void changeThought(int gedachte, String change) {
        try {
            java.sql.Connection con = Database.getConnection();
            java.sql.Statement s = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            int index = gedachte + 1;

            ResultSet thought;
            thought = s.executeQuery("select * from thoughts");
            thought.absolute(index);
            thought.updateString("notes", change);
            thought.updateRow();

        } catch (SQLException ex) {
            Logger.getLogger(DataLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ArrayList getProjects() {
        try {
            ArrayList<String> arraylist = new ArrayList<String>();
            java.sql.Connection con = Database.getConnection();
            java.sql.Statement s = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ResultSet projects;
            projects = s.executeQuery("select * from projects");
            if (projects.next()) {
                projects.first();
                arraylist.add(projects.getInt("id") + " " + projects.getString("name"));

                while (projects.next()) {
                    arraylist.add(projects.getInt("id") + " " + projects.getString("name"));
                }

            }

            return arraylist;
        } catch (SQLException ex) {
            Logger.getLogger(DataLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void deleteProject(int id) {
        try {
            java.sql.Connection con = Database.getConnection();
            java.sql.Statement s = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ResultSet projects;
            projects = s.executeQuery("select * from projects");
            projects.absolute(id);
            projects.deleteRow();

            int size = aantalID("projects");
            projects = s.executeQuery("select * from projects");
            if (projects.next()) {
                while (id < size) {
                    projects.absolute(id);
                    projects.updateInt("id", id);
                    projects.updateRow();
                    id++;
                    projects.next();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void changeProject(int id, String change) {
        try {
            java.sql.Connection con = Database.getConnection();
            java.sql.Statement s = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ResultSet thought;
            thought = s.executeQuery("select * from projects");
            thought.absolute(id);
            thought.updateString("name", change);
            thought.updateRow();
        } catch (SQLException ex) {
            Logger.getLogger(DataLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String addProject(String naam) {
        try {
            java.sql.Connection con = Database.getConnection();
            java.sql.Statement s = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            int id = aantalID("projects");

            ResultSet projects;
            projects = s.executeQuery("select * from projects");
            projects.moveToInsertRow();
            projects.updateInt("id", id);
            projects.updateString("name", naam);
            projects.updateString("notes", "test");
            projects.insertRow();

            return id + " " + naam + " test";

        } catch (SQLException ex) {
            Logger.getLogger(DataLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ArrayList getStatuses() {
        try {
            ArrayList<String> arraylist = new ArrayList<String>();

            java.sql.Connection con = Database.getConnection();
            java.sql.Statement s = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ResultSet status;
            status = s.executeQuery("select * from statuses");
            status.first();

            arraylist.add(status.getString("name"));
            while (status.next()) {
                String input = status.getString("name");
                arraylist.add(input);
            }

            return arraylist;
        } catch (SQLException ex) {
            Logger.getLogger(DataLayer.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public static void addAction(String description, String status, int projectid, String datum) {
        try {
            java.sql.Connection con = Database.getConnection();
            java.sql.Statement s = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ResultSet statuses;
            statuses = s.executeQuery("select * from statuses");
            statuses.first();
            boolean klaar = false;
            int statusid = 0;
            while (!klaar) {
                if (statuses.getString("name").equals(status)) {
                    statusid = statuses.getInt("id");
                    klaar = true;
                } else {
                    statuses.next();
                }
            }


            int actionid = aantalID("actions");

            ResultSet actions;
            actions = s.executeQuery("select * from actions");
            actions.moveToInsertRow();
            actions.updateInt("id", actionid);
            actions.updateString("description", description);
            actions.updateInt("status_id", statusid);
            actions.updateInt("project_id", projectid);
            actions.updateDate("action_date", Date.valueOf(datum));
            actions.updateBoolean("done", false);
            actions.insertRow();

        } catch (SQLException ex) {
            Logger.getLogger(DataLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ArrayList getActions(int id) {
        try {
            ArrayList<String> arraylist = new ArrayList<String>();
            java.sql.Connection con = Database.getConnection();
            java.sql.Statement s = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ResultSet actions;

            actions = s.executeQuery("select description from actions where project_id = " + id);

            if (actions.next()) {
                actions.first();
                arraylist.add(actions.getString("description"));

                while (actions.next()) {
                    arraylist.add(actions.getString("description"));
                }
            }
            return arraylist;
        } catch (SQLException ex) {
            Logger.getLogger(DataLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}