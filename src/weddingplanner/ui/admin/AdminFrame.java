/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weddingplanner.ui.admin;

import weddingplanner.model.Admin;
import weddingplanner.ui.SwingUtils;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author laila-elhattab
 */
public class AdminFrame extends JFrame {

    JTabbedPane tp;

    public AdminFrame(Admin admin) {
        super("Administrator");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setBackground(new Color(219, 201, 201));
        setLayout(new BorderLayout());

        JLabel jl=new JLabel();
        String str = "<html><b>"+admin.getName()+"<b><br><span style=\"color:blue\"> "+admin.getEmail()+"</span></html>";
        jl.setText(str);

        JLabel title = new JLabel("Wedding Planner");
        title.setFont(new Font("Georgia Pro Cond", 2, 24));

        jl.setHorizontalAlignment(SwingConstants.RIGHT);
        jl.setVerticalAlignment(SwingConstants.CENTER);
        jl.setBorder(BorderFactory.createEmptyBorder(5,5,5,10));
        add(jl, BorderLayout.SOUTH);
        add(title, BorderLayout.NORTH);

        
        tp = new JTabbedPane();
        JPanel p1 = new EventsTable();
        p1.setBackground(new Color(219, 201, 201));
         try {
            UIManager.setLookAndFeel(
                    UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        JPanel p2 = new ClientTable();
        p2.setBackground(new Color(219, 201, 201));
        JPanel p3 = new PlannersTable(this);
        p3.setBackground(new Color(219, 201, 201));
        tp.add("Events", p1);
        tp.add("Users", p2);
        tp.add("Planners", p3);
        add(tp,BorderLayout.CENTER);
        
        
        setVisible(true); 
    }

    
}
