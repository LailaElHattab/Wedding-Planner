/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weddingplanner.ui.admin;

import weddingplanner.managers.EventManager;
import weddingplanner.model.Event;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
 * @author laila-elhattab
 */

public class EventsTable extends JPanel {
    List<Event> events;
    JTable table;
    DefaultTableModel tableModel;

    public EventsTable() {
        this.setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(0, 0);
        String[] columnNames = {"Client Email", "Planner Email", "Planner response", "State", "Actions"};
        tableModel.setColumnIdentifiers(columnNames);

        events = EventManager.getInstance().loadAll();

        for (int i = 0; i < events.size(); i++) {
            tableModel.addRow(convertToRow(events.get(i)));
        }

        table = new JTable(tableModel);
        table.setBackground(new Color(245, 238, 238));
        table.setDefaultEditor(Object.class, null);
        table.getColumn("Actions").setCellRenderer(new ButtonRenderer());
        table.getColumn("Actions").setCellEditor(new ButtonEditor(new JCheckBox(), this));

        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    private Object[] convertToRow(Event event){
        Object[] row = new Object[5];
        row[0] = event.getClientEmail();
        row[1] = event.getPlannerEmail();
        row[2] = event.getPlannerResponse();
        row[3] = event.getStatus();
        row[4] = "Edit";
        return row;
    }

    public void updateEvent(Event event, int row){
        Object[] data = convertToRow(event);
        for(int i=0; i< data.length; i++){
            tableModel.setValueAt(data[i],row,i);
        }
    }


    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText("Edit");
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {

        protected JButton button;

        public ButtonEditor(JCheckBox checkBox, EventsTable eventsTable) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JFrame frame = new JFrame();
                    frame.setLocationRelativeTo(null);
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    int selection = table.getSelectedRow();
                    EventDetailsDialog dets = new EventDetailsDialog(frame, eventsTable,events.get(selection),selection);
                    dets.setVisible(true);

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
            button.setText("Edit");

            return button;
        }
    }
}
        