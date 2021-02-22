/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weddingplanner.ui;

import weddingplanner.managers.UsersManager;
import weddingplanner.model.Admin;
import weddingplanner.model.Client;
import weddingplanner.model.Planner;
import weddingplanner.model.User;
import weddingplanner.ui.admin.AddPlannerDialog;
import weddingplanner.ui.admin.AdminFrame;
import weddingplanner.ui.client.ClientFrame;
import weddingplanner.ui.planner.PlannerFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * @author laila-elhattab
 */
public class LoginFrame extends JFrame implements ActionListener {
    JTextField jtEmail;
    JPasswordField jtPassword;
    JButton jbLogin;
    JButton jbSignup;
    JButton jbCancel;
    JLabel msgLbl;
    JLabel title;
    public LoginFrame() {
        super("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        this.setSize(677, 355);
        setLocationRelativeTo(null);

        jtEmail = new JTextField(15);
        jtPassword = new JPasswordField(15);
        msgLbl = new JLabel("");
        msgLbl.setForeground(Color.RED);
        msgLbl.setHorizontalAlignment(SwingConstants.CENTER);
        jbLogin = new JButton("Login");
        jbLogin.addActionListener(this);
        jbSignup = new JButton("Signup");
        jbSignup.addActionListener(this);
        jbCancel = new JButton("Cancel");
        jbCancel.addActionListener(this);
        title = new JLabel("Wedding Planner");
        title.setFont(new Font("Georgia Pro Cond", 2, 24));

        JPanel logoPanel = new JPanel();
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("ring.png"));
        logoPanel.add(new JLabel(logoIcon));
        add(logoPanel, BorderLayout.WEST);
        logoPanel.add(title, BorderLayout.SOUTH);
        logoPanel.setBackground(new Color(245, 238, 238));

        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipady = 5;
        gbc.anchor = GridBagConstraints.WEST;

        fieldsPanel.setBackground(new Color(245, 238, 238));
        fieldsPanel.add(new JLabel("Email: "), gbc);
        gbc.gridy++;
        fieldsPanel.add(new JLabel("Password: "), gbc);
        gbc.gridy++;
        gbc.gridwidth = 2;
        fieldsPanel.add(msgLbl, gbc);
        gbc.gridwidth = 1;

        gbc.gridx++;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        fieldsPanel.add(jtEmail, gbc);
        gbc.gridy++;
        fieldsPanel.add(jtPassword, gbc);
        add(fieldsPanel, BorderLayout.CENTER);


        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));
        buttonsPanel.add(jbLogin);
        buttonsPanel.add(jbSignup);
        buttonsPanel.add(jbCancel);
        buttonsPanel.setBackground(new Color(245, 238, 238));
        add(buttonsPanel, BorderLayout.SOUTH);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(jbLogin)) {
            User user = UsersManager.getInstance().login(jtEmail.getText(), jtPassword.getPassword());
            if (user == null) {
                msgLbl.setText("invalid email or password");
                return;
            }
            if (user instanceof Admin) {
                this.setVisible(false);
                new AdminFrame((Admin) user);
            } else if (user instanceof Client) {
                Client client = (Client) user;
                if (!client.isActive()) {
                    msgLbl.setText("The administrator didn't activate your account yet.");
                    return;
                }
                this.setVisible(false);
                new ClientFrame(client);
            } else if (user instanceof Planner) {
                this.setVisible(false);
                new PlannerFrame((Planner) user);
            } else {
                msgLbl.setText(user.getClass().getSimpleName());
            }
        } else if (e.getSource().equals(jbSignup)) {
            SignupDialog signupDialog = new SignupDialog(this);
            signupDialog.setVisible(true);
        } else if (e.getSource().equals(jbCancel)) {
            System.exit(0);
        }
    }


}
