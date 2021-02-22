/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weddingplanner.ui.admin;

import weddingplanner.managers.UsersManager;
import weddingplanner.model.Planner;
import weddingplanner.model.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author laila-elhattab
 */
public class PlannersTable extends JPanel implements ActionListener{
    JButton button;
    List<User> users = UsersManager.getInstance().loadAll();
    List<Planner> planners;
    JTable table;
    DefaultTableModel tableModel;

    private JFrame ownerFrame;
    public PlannersTable(JFrame ownerFrame) {
        this.ownerFrame = ownerFrame;
        this.setLayout(new BorderLayout());
        tableModel = new DefaultTableModel(0, 0);
        String[] columnNames = {"Name", "Email", "Planner info"};
        tableModel.setColumnIdentifiers(columnNames);

        planners=new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i) instanceof Planner) {
                addPlanner((Planner) users.get(i));
            }
        }

        table = new JTable(tableModel);
        table.setBackground(new Color(245, 238, 238));
        table.setDefaultEditor(Object.class, null);

        table.getColumn("Planner info").setCellRenderer(new ButtonRenderer());
        table.getColumn("Planner info").setCellEditor(new ButtonEditor(new JCheckBox()));

        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);
        

        button=new JButton("Add Planner");
        button.addActionListener(this);
        this.add(button,BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==button){
            AddPlannerDialog planner=new AddPlannerDialog(ownerFrame, this);
            planner.setVisible(true);
        }
    }

    public void addPlanner(Planner planner){
        this.planners.add(planner);
        Object[] row = new Object[3];
        row[0] = planner.getName();
        row[1] = planner.getEmail();
        row[2] = "view";
        tableModel.addRow(row);
    }


class ButtonRenderer extends JButton implements TableCellRenderer {

    public ButtonRenderer() {
        setOpaque(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText("View");
        return this;
    }
}

class ButtonEditor extends DefaultCellEditor {

    protected JButton button;

    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PlannersDetailsDialog plannerDialog=new PlannersDetailsDialog(ownerFrame,planners.get(table.getSelectedRow()));
                plannerDialog.setVisible(true);
            }
        });
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }
        button.setText("View");

        return button;
    }
}
}
