package weddingplanner.managers;


import weddingplanner.model.Event;
import weddingplanner.model.Planner;
import weddingplanner.model.Admin;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.util.Properties;

/**
 * @author laila-elhattab
 */

public class EmailManager {
    //Sends email
    public static void sendEmail(String toAddress, String subject, String body) {
        Properties prop = System.getProperties();

        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("weddingplannertest1@gmail.com", "Weddingplanner");
                    }
                });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("weddingplannertest1@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
            JOptionPane.showMessageDialog(null, "Message sent successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Sends Activation mail to client
    public static void sendClientActivationEmail(String clientEmail, String clientName, boolean active) {
        String subject = "Account Activation";
        String body = "Dear " + clientName + " ," + '\n'
                + "Thank you so much for trusting us with your event. " + '\n' + "We are thrilled to be part of your special day. " + '\n'
                + "You are now ready to book and plan your day so please take a look on all the services available." + '\n' + "Thank you," + '\n' + "planMe team";
        EmailManager.sendEmail(clientEmail, subject, body);
    }

    //Sends an email to the planner along with the password
    public static void sendAddingPlanerEmail(Planner planner, String plannerName) {
        String subject = "Account Activation";
        char [] pass=Admin.setrandpass().toCharArray();
        planner.setPassword(pass);
        String body = "Dear " + plannerName + " ," + '\n'
                + "Thank you for your interest in being part of planMe . " + '\n' + "We are thrilled to tell you that we are welcoming you on board. " + '\n'
                +"Here is your password: "+pass.toString()+'\n'
                + "You are now ready to plan the assigned clients events " + '\n' + "Thank you," + '\n' + "planMe team";
        EmailManager.sendEmail(planner.getEmail(), subject, body);
    }

    //Send acceptance email
    public static void sendAcceptEventEmail(String ClientEmail,String clientName, String plannerName, Event event) {
        String subject = "Event Confirmed";
        String body = "Dear " + clientName + " ," + '\n'
                + "Thank you so much for trusting us with your "+event.getType() +". "+ '\n' + plannerName + " and us truly hope you have a wonderful time. " + '\n'
                + "If you have any inquiries, please contact the admin." + '\n' + "Thank you," + '\n' + "planMe team";
        EmailManager.sendEmail(ClientEmail, subject, body);

    }

    //Send rejection email
    public static void sendRejectEventEmail(String ClientEmail,String clientName, String plannerName, Event event) {
        String subject = "Event Confirmed";
        String body = "Dear " + clientName + " ," + '\n'
                + "Thank you so much for trusting us with your "+event.getType() +". "+ '\n' + plannerName + " and us are truly sorry to have issues with going through with your " +event.getType()+"." +'\n'
                + "But we can have a meeting and discuss all the changes needed" + '\n' + "Thank you," + '\n' + "planMe team";
        EmailManager.sendEmail(ClientEmail, subject, body);
    }


}
