package me.Anthony.Mail;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.StringWriter;
import java.util.List;
import java.util.Properties;

/**
 * Created by Anthony on 7/10/2016.
 */
public class Email {

    private Sender s;
    private String to;
    private VelocityEngine v = null;

    public Email(Sender s, String to) {
        this.s = s;
        this.to = to;
    }

    public boolean isAuthenticated() {
        return s.isAuthenticated();
    }

    public VelocityEngine getVelocityEngine(){

        VelocityEngine ve = new VelocityEngine();
        Properties props = new Properties();
        String path = "src/me/Anthony/Mail/";
        props.put("file.resource.loader.path", path);
        props.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogSystem");
        ve.init(props);
        return ve;
    }

    public boolean send(String Subject, List<String> text) {
        if(s.isAuthenticated()) {
            try {
                MimeMessage message = new MimeMessage(s.getSession());
                InternetAddress address = new InternetAddress(s.getUsername());
                address.setPersonal(s.getSenderName());
                message.setFrom(address);
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                message.setSubject(Subject);
                StringBuilder builder = new StringBuilder();
                for(int i = 0; i < text.size(); i++) {
                    builder.append(text.get(i)).append(" ").append("\n");
                }
                Template template = null;
                VelocityContext context = new VelocityContext();
                StringBuilder s = new StringBuilder();
                for(int i = 0; i < text.size(); i++) {
                    s.append(text.get(i)).append(" ").append("\n");
                }
                context.put("Name", Subject);
                context.put("Text", s.toString());
                context.put("Signature", this.s.getSenderName());
                v  = getVelocityEngine();
                try {
                    template = v.getTemplate("EmailTemplate.vm");
                } catch(Exception e) {
                    System.out.println("Could not load template!");
                    e.printStackTrace();
                }
                if(template != null) {
                    StringWriter writer = new StringWriter();
                    template.merge(context, writer);
                    message.setContent(writer.toString(), "text/html; charset=utf-8");
                    Transport.send(message);
                    System.out.println("\n" + "Successfully sent mail to " + to.toString() + " with contents : " + "\n" + writer.toString());
                    return true;
                } else {
                    message.setText(builder.toString());
                    Transport.send(message);
                    System.out.println("\n" + "Successfully sent mail to " + to.toString() + " with contents : " + "\n" + builder.toString());
                    return true;
                }
            } catch (Exception e) {
                System.out.println("Could not send message!");
                e.printStackTrace();
                return false;
            }
        } else {
            System.out.println("Please authenticate your password before sending an email!");
        }
        return false;
    }

    @Override
    public String toString() {
        return "me.Anthony.Mail.Email/" + s.getUsername() + "/to/" + to.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
