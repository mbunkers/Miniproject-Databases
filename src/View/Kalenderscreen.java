/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Marc Bunkers, Fabian Claasen
 */
public class Kalenderscreen extends JPanel{

    public Kalenderscreen() {
        setBackground(Color.RED);
        JLabel test = new JLabel("Kalender");
        add(test);
    }
}
