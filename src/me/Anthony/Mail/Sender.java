package me.Anthony.Mail;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

/**
 * Created by Anthony on 7/11/2016.
 */
public class Sender extends Object implements MailMan {

    private String username;
    private String password;
    private boolean authenticated = false;
    private Session session;
    private String senderName;

    public Sender(String username, String password, String senderName) {
        this.username = username;
        this.password = password;
        this.senderName = senderName;
        if(authenticate()) {
            this.authenticated = true;
        }
    }

    @Override
    public boolean authenticate() {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", "465");
        System.out.println("Sending SSL data");
        System.out.println("Attempting to Send Password Information");
        try {
            session = Session.getInstance(prop, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
            System.out.print("Password Authenticated!");
            return true;
        } catch(Exception e) {
            System.out.println("Could not authenticate password - please provide valid login details for account " + username.toString() + "!");
            return false;
        }
    }

    public String getSenderName() {
        return senderName;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public Session getSession() {
        return session;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public String toString() {
        return "MailMan/Sender/" + username;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }



}


