package me.Anthony.Mail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Anthony on 7/11/2016.
 */
public class EmailManager {

    public static EmailManager manager;

    //ArrayList for Emails to be added to for blasts
    private ArrayList<Email> emails = new ArrayList<>();

    //Making one sender for email blasts
    private Sender sender;

    //Making EmailManager private so you can only get one instance of it
    private EmailManager() { }

    //Making sure only one instance of the EmailManager class is always used
    public static EmailManager getManager() {
        if(manager == null) {
            manager = new EmailManager();
        }
        return manager;
    }

    public void setSender(Sender s) {
        this.sender = s;
    }

    public boolean sendBlast(String Subject, List<String> content) {
        try {
          for(int i = 0; i < emails.size(); i++) {
              System.out.println("Sending Email Blast!");
              emails.get(i).send(Subject, content);
              archive(emails.get(i));
              emails.remove(i);
          }
        } catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("Email blast finished! Sent " + emails.size() + " to " + emails.size() + " authenticated users!");
        return true;
    }

    public boolean sendTimedBlast(String Subject, ArrayList<String> content, int inSeconds) {
        Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(new Runnable() {
            public void run() {
                try {
                        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = new Date();
                        String start = sdfDate.format(date);
                    System.out.println("Sending Email Blast @: " + start);
                    for (int i = 0; i < emails.size(); i++) {
                        emails.get(i).send(Subject, content);
                        archive(emails.get(i));
                        emails.remove(i);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },0, inSeconds, TimeUnit.SECONDS);
        System.out.println("Email blast finished! Sent " + emails.size() + " to " + emails.size() + " authenticated users!");
        return true;
    }

    public boolean addEmail(Email e) {
        if(e.isAuthenticated()) {
            emails.add(e);
            System.out.println("Successfully Added Authenticated Email!");
            return true;
        }
        System.out.println("Could not add email due to its sender not being authenticated!");
        return false;
    }

    public boolean removeEmail(Email e) {
        if(emails.contains(e)) {
            emails.remove(e);
            return true;
        }
        return false;
    }

    public boolean containsEmail(Email e) {
        if(emails.contains(e)) {
            emails.remove(e);
            return true;
        }
        return false;
    }

    public void archive(Email e) {
          //Work in progress
    }








}
