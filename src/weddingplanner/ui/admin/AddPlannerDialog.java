/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weddingplanner.ui.admin;

import weddingplanner.managers.EmailManager;
import weddingplanner.managers.EventManager;
import weddingplanner.managers.UsersManager;
import weddingplanner.model.Planner;
import weddingplanner.ui.SwingUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author laila-elhattab
 */
public class AddPlannerDialog extends JDialog implements ActionListener {

    JTextField jtName;
    JPasswordField jtPassword;
    JPasswordField jtConfirm;
    JTextField jtEmail;
    JTextField jtExYears;
    JTextField jtSalary;
    JButton jbPhoto;
    JButton jBSave;
    JButton jBCancel;
    JLabel lbEmailErr;
    JLabel lbPassErr;

    PlannersTable plannersTable;

    AddPlannerDialog(JFrame owner, PlannersTable plannersTable) {

        super(owner, "New Planner", true);
        this.plannersTable = plannersTable;
        setLayout(new BorderLayout());

        jtName = new JTextField(15);
        jtEmail = new JTextField(15);
        lbEmailErr = new JLabel("");
        lbEmailErr.setForeground(Color.RED);
        jtPassword = new JPasswordField(15);
        jtConfirm = new JPasswordField(15);
        lbPassErr = new JLabel("");
        lbPassErr.setForeground(Color.RED);
        jtExYears = new JTextField(15);
        SwingUtils.allowDigitsOnly(jtExYears);
        jtSalary = new JTextField(15);
        SwingUtils.allowDigitsOnly(jtSalary);
        jbPhoto = new JButton("");
        jbPhoto.setIcon(new ImageIcon(AddPlannerDialog.class.getResource("photo.png")));
        jbPhoto.setPreferredSize(new Dimension(40, 40));
        jbPhoto.addActionListener(this);
        jBSave = new JButton("Save");
        jBCancel = new JButton("Cancel");
        jBSave.addActionListener(this);
        jBCancel.addActionListener(this);



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
        gbc.gridy++;
        fieldsPanel.add(new JLabel("Years of Experience: "), gbc);
        gbc.gridy++;
        fieldsPanel.add(new JLabel("Salary: "), gbc);
        gbc.gridy++;
        fieldsPanel.add(new JLabel("Photo: "), gbc);


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
        gbc.gridy++;
        gbc.gridy++;
        fieldsPanel.add(jtExYears, gbc);
        gbc.gridy++;
        fieldsPanel.add(jtSalary, gbc);
        gbc.gridy++;
        fieldsPanel.add(jbPhoto, gbc);
        add(fieldsPanel, BorderLayout.CENTER);


        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(new Color(245, 238, 238));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonsPanel.add(jBSave);
        buttonsPanel.add(jBCancel);
        add(buttonsPanel, BorderLayout.SOUTH);

        this.pack();
        setLocationRelativeTo(owner);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(jBSave)) {
            if (jtEmail.getText().length() == 0 || jtPassword.getPassword().length == 0 || jtName.getText().length() == 0) {
                JOptionPane.showMessageDialog(this, "Please continue filling the empty fields");

            } else {
                if (!new String(jtPassword.getPassword()).equals(new String(jtConfirm.getPassword()))) {
                    lbPassErr.setText("doesn't match the password");
                    return;
                }
                lbPassErr.setText("");
                Planner planner = new Planner(jtName.getText(), jtEmail.getText(), jtPassword.getPassword());
                if (UsersManager.getInstance().check(planner)) {
                    try {
                        lbEmailErr.setText("");
                        planner.setPhotoFile(jbPhoto.getText());
                        planner.setExYears(Integer.parseInt(jtExYears.getText()));
                        planner.setSalary(Integer.parseInt(jtSalary.getText()));
                        UsersManager.getInstance().add(planner);
                        JOptionPane.showMessageDialog(this, "The planner has been added.");
                        plannersTable.addPlanner(planner);
                        this.setVisible(false);
                        EmailManager.sendAddingPlanerEmail(planner, planner.getName());

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Sorry, the planner can't be added. Please contact the administrator");
                    }
                } else {
                    lbEmailErr.setText("Sorry, this Username is already taken");
                }
            }
        } else if (e.getSource().equals(jbPhoto)) {
            JFileChooser jfc = new JFileChooser("photos");
            jfc.setDialogTitle("Choose a planner photo");
            jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG and JPEG images ", "jpg", "jpeg", "png");
            jfc.addChoosableFileFilter(filter);
            jfc.setAcceptAllFileFilterUsed(false);
            int returnValue = jfc.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                String rootPath = new File("").getAbsolutePath();
                String selectedFile = jfc.getSelectedFile().getAbsolutePath();
                if(selectedFile.startsWith(selectedFile)){
                    selectedFile = selectedFile.substring(rootPath.length() + 1);
                }
                jbPhoto.setText(selectedFile);
            }
        }
        else {
            this.setVisible(false);
        }
    }
}
