/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Actie;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

/**
 *
 * @author Marc Bunkers, Fabian Claasen
 */
public class Actiescreen extends JPanel{
    private JScrollBar scrollBar;
    private JPanel actie1, actie2, actie3,  kalender;
    
    private Actie actie;
    
    private int teller = 0;
    
    public Actiescreen(){
        actie = new Actie();
        //maakActie();
        
        create();
        
        setLayout(new BorderLayout());
        
        add(new Menubalk(), BorderLayout.NORTH);
        add(scrollBar, BorderLayout.WEST);
        add(kalender, BorderLayout.EAST);
    }
    
    private void create(){
        scrollBar = new JScrollBar();
        scrollBar.setLayout(new FlowLayout());
        
        actie1 = new JPanel();
        actie1.setPreferredSize(new Dimension(150, 600));
        actie2 = new JPanel();
        actie2.setPreferredSize(new Dimension(150, 600));
        actie3 = new JPanel();
        actie3.setPreferredSize(new Dimension(150, 600));
        kalender = new JPanel();
        kalender.setPreferredSize(new Dimension(150, 600));
        
        scrollBar.add(actie1);
        scrollBar.add(actie2);
        scrollBar.add(actie3);
    }
    
//    private void maakActie(){
//        while(actie.hasNext()){
//            if(teller <= 0){
//                if(actie.getActionDescription().equals("")){
//                
//                }else{
//                    JButton button = new JButton(actie.getActionDescription());
//                    button.addActionListener(this);
//                    actie1.add(button);
//                    teller = 1;
//                }
//            }
//        
//            if(teller == 1){
//                if(actie.getActionDescription().equals("")){
//                
//                }else{
//                    JButton button = new JButton(actie.getActionDescription());
//                    button.addActionListener(this);
//                    actie2.add(button);
//                    teller++;
//                }
//            }
//            if(teller >= 2){
//                if(actie.getActionDescription().equals("")){
//                
//                }else{
//                    JButton button = new JButton(actie.getActionDescription());
//                    button.addActionListener(this);
//                    actie3.add(button);
//                    teller = 0;
//                }
//            }
//        }
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        //
//    }
}
