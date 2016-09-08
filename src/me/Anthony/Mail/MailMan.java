package me.Anthony.Mail;

import javax.mail.Session;

/**
 * Created by Anthony on 7/11/2016.
 */
public interface MailMan {

    boolean authenticate();
    boolean isAuthenticated();
    Session getSession();
    String getSenderName();
    String getUsername();

}
