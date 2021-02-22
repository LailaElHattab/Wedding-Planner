/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weddingplanner.ui;

import weddingplanner.managers.UsersManager;
import weddingplanner.model.Client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.*;

/**
 * @author laila-elhattab
 */
public class SignupDialog extends JDialog implements ActionListener {

    JTextField jtName;
    JPasswordField jtPassword;
    JPasswordField jtConfirm;
    JTextField jtEmail;
    JLabel lbEmailErr;
    JLabel lbPassErr;

    JButton jSign;
    JButton jCancel;

    public SignupDialog(JFrame owner) {
        super(owner, "Signup", true);
        setSize(300, 400);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 1));

        setLayout(new BorderLayout());

        jtName = new JTextField(15);
        jtEmail = new JTextField(15);
        lbEmailErr = new JLabel("");
        lbEmailErr.setForeground(Color.RED);
        jtPassword = new JPasswordField(15);
        jtConfirm = new JPasswordField(15);
        lbPassErr = new JLabel("");
        lbPassErr.setForeground(Color.RED);

        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        fieldsPanel.add(new JLabel("Name: "), gbc);
        gbc.gridy++;
        fieldsPanel.add(new JLabel("Email: "), gbc);
        gbc.gridy++;
        gbc.gridwidth = 2;
        fieldsPanel.add(lbEmailErr, gbc);
        gbc.gridwidth = 1;
        gbc.gridy++;
        fieldsPanel.add(new JLabel("Password: "), gbc);
        gbc.gridy++;
        fieldsPanel.add(new JLabel("Confirm Password: "), gbc);
        gbc.gridy++;
        gbc.gridwidth = 2;
        fieldsPanel.add(lbPassErr, gbc);
        gbc.gridwidth = 1;

        gbc.gridx++;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        fieldsPanel.add(jtName, gbc);
        gbc.gridy++;
        fieldsPanel.add(jtEmail, gbc);
        gbc.gridy++;
        gbc.gridy++;
        fieldsPanel.add(jtPassword, gbc);
        gbc.gridy++;
        fieldsPanel.add(jtConfirm, gbc);
        add(fieldsPanel, BorderLayout.CENTER);


        jSign = new JButton("Signup");
        jCancel = new JButton("Cancel");
        jSign.addActionListener(this);
        jCancel.addActionListener(this);
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonsPanel.add(jSign);
        buttonsPanel.add(jCancel);
        add(buttonsPanel, BorderLayout.SOUTH);

        this.pack();
        setLocationRelativeTo(owner);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(jSign)) {
            if (jtEmail.getText().length() == 0 || jtPassword.getPassword().length == 0 || jtName.getText().length() == 0) {
                JOptionPane.showMessageDialog(null, "Please continue filling the empty fields");

            } else {
                if (!new String(jtPassword.getPassword()).equals(new String(jtConfirm.getPassword()))) {
                    lbPassErr.setText("doesn't match the password");
                    return;
                }
                lbPassErr.setText("");
                Client user = new Client(jtName.getText(), jtEmail.getText(), jtPassword.getPassword());
                user.setActive(false);
                user.setRegistrationDate(new Date().getTime());
                if (UsersManager.getInstance().check(user)) {
                    try {
                        lbEmailErr.setText("");
                        UsersManager.getInstance().add(user);
                        JOptionPane.showMessageDialog(this, "Your request will be reviewed by the administrator.");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Sorry, this User can't be added. Please contact the administrator");
                    }
                    System.exit(0);
                } else {
                    lbEmailErr.setText("Sorry, this Email is already taken");
                }
            }
        } else {
            System.exit(0);
        }
    }
}


