/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weddingplanner;

import weddingplanner.ui.LoginFrame;

import javax.swing.UIManager;

/**
 * @author laila-elhattab
 */
public class WeddingPlannerApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);
    }

}
