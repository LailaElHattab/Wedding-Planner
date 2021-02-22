/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weddingplanner.ui.admin;

import weddingplanner.managers.DateUtils;
import weddingplanner.managers.EmailManager;
import weddingplanner.managers.UsersManager;
import weddingplanner.model.Client;
import weddingplanner.model.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 * @author laila-elhattab
 */
public class ClientTable extends JPanel {

    List<User> users = UsersManager.getInstance().loadAll();
    List<Client> clients;
    JTable table;
    DefaultTableModel tableModel;

    public ClientTable() {
        this.setLayout(new BorderLayout());

        String[] columnNames = {"Name", "Email", "Date of registration", "Status", "Action"};
        tableModel = new DefaultTableModel(0, 0);
        tableModel.setColumnIdentifiers(columnNames);

        clients = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i) instanceof Client) {
                Client client = (Client) users.get(i);
                clients.add(client);
                Object[] row = new Object[5];
                row[0] = client.getName();
                row[1] = client.getEmail();
                row[2] = DateUtils.toString(client.getRegistrationDate());
                row[3] = client.isActive() ? "Activated" : "Deactivated";
                row[4] = client.isActive();
                tableModel.addRow(row);
            }
        }

        table = new JTable(tableModel);

        table.setDefaultEditor(Object.class, null);
        table.setBackground(new Color(245, 238, 238));
        table.getColumn("Action").setCellRenderer(new ButtonRenderer());
        table.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));

        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);
    }


    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            this.setText(value != Boolean.TRUE ? "Activate" : "Deactivate");
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
                    try {
                        int row = table.getSelectedRow();
                        Client client = clients.get(row);
                        client.setActive(!client.isActive());
                        UsersManager.getInstance().edit(client);
                        tableModel.setValueAt(client.isActive() ? "Activated" : "Deactivated", row, 3);
                        tableModel.setValueAt(client.isActive(), row, 4);
                        button.setText(!client.isActive() ? "Activate" : "Deactivate");
                        EmailManager.sendClientActivationEmail(client.getEmail(), client.getName(), client.isActive());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Sorry, this User can't be activated. Please contact the administrator");
                    }

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
            button.setText(value != Boolean.TRUE ? "Activate" : "Deactivate");

            return button;
        }

        public Object getCellEditorValue() {
            return !button.isEnabled();
        }
    }
}