package HomeworkPlanner.EmailSender;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.Properties;

public class Email {

    public void send(String toEmail, String subject, String htmlContent) {
        String username = "";
        String pass = "";

        Map<String, String> env = System.getenv();

        for (String envName : env.keySet()) {
            if (envName.equalsIgnoreCase("USER")) {
                username = env.get(envName).replaceAll("\"", "");
            } else if (envName.equalsIgnoreCase("PASS")) {
                pass = env.get(envName).replaceAll("\"", "");
            }
        }

        Properties var3 = new Properties();
        var3.setProperty("mail.smtp.host", "smtp.gmail.com");
        var3.setProperty("mail.smtp.port", "465");
        var3.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        var3.setProperty("mail.smtp.socketFactory.port", "465");
        var3.setProperty("mail.smtp.auth", "true");
        var3.setProperty("mail.smtp.starttls.enable", "true");

        String finalPass = pass;
        String finalUsername = username;
        Session session = Session.getDefaultInstance(var3, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(finalUsername, finalPass);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

            message.setSubject(subject);
            message.setContent(htmlContent, "text/html");

            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
