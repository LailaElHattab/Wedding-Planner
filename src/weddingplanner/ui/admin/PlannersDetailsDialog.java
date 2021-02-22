/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weddingplanner.ui.admin;

import weddingplanner.managers.DateUtils;
import weddingplanner.managers.EventManager;
import weddingplanner.model.Planner;
import weddingplanner.model.Event;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.Border;

/**
 *
 * @author laila-elhattab
 */
public class PlannersDetailsDialog extends JDialog implements ActionListener{
    ImageIcon image;
    JLabel jlName;
    JLabel jEx;
    JLabel jlEmail;
    JLabel jSalary;
    JButton jback;

    PlannersDetailsDialog(JFrame owner, Planner planner){
        super(owner,"Planner Details",true);
        setSize(500, 500);
        this.setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(new Color(245, 238, 238));
        infoPanel.setLayout(new FlowLayout());
        
        JPanel p2 = new JPanel();
        p2.setBackground(new Color(245, 238, 238));
        image = new ImageIcon(planner.getPhotoFile());
        JLabel jl = new JLabel(image);
        p2.add(jl);

        jlName = new JLabel("Name: " + planner.getName());
        jlEmail = new JLabel("Email: " + planner.getEmail());
        jEx=new JLabel("Years of experience: "+planner.getExYears());
        jSalary=new JLabel("Salary: "+planner.getSalary());
        JPanel p3=new JPanel();
        p3.setBackground(new Color(245, 238, 238));
        p3.setLayout(new GridLayout(4, 1));
        p3.add(jlName);
        p3.add(jlEmail);
        p3.add(jEx);
        p3.add(jSalary);
        
        infoPanel.add(p2);
        infoPanel.add(p3);

        
        List<Event> events= EventManager.getInstance().loadAll();
        List<Event> myEvents =new ArrayList<>();
        
        for(int i=0;i<events.size();i++){
            Event event = events.get(i);
            if(event.getPlannerEmail()!=null && event.getPlannerEmail().equals(planner.getEmail())){
                myEvents.add(event);
            }
        }
        String[] columnNames = {"Client Email","Event", "Date", "Planner response", "State"};
        Object[][] data = new Object[myEvents.size()][5];
        for (int i = 0; i < myEvents.size(); i++) {
            data[i][0] = myEvents.get(i).getClientEmail();
            data[i][1] = myEvents.get(i).getType();
            data[i][2] = DateUtils.toString(myEvents.get(i).getEventDate());
            data[i][3] = myEvents.get(i).getPlannerResponse();
            data[i][4] = myEvents.get(i).getStatus();
        }

        JPanel tablePanel=new JPanel(new BorderLayout());
        tablePanel.setBackground(new Color(245, 238, 238));
        Border border = BorderFactory.createTitledBorder("Assigned Events");
        tablePanel.setBorder(border);

        JTable table=new JTable(data,columnNames);
        table.setBackground(new Color(219, 201, 201));
        table.setDefaultEditor(Object.class, null);
        JScrollPane scroll=new JScrollPane(table);
        tablePanel.add(scroll, BorderLayout.CENTER);
        
        JPanel footerPanel=new JPanel();
        footerPanel.setBackground(new Color(245, 238, 238));
        jback=new JButton("OK");
        jback.addActionListener(this);
        footerPanel.add(jback);
        
        add(infoPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);
        
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==jback){
         this.dispose();
        }
    }
}
