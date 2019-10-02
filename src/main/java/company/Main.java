package company;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Properties;

public class Main {
    private static ArrayList<String> mail = new ArrayList<>(); // Add to mail list

    // FIELDS TO FILL IN.
    private static final String themes = "Spamming"; // The title of the letter (optional)
    private static final String text = "Hello, my friend"; // Text message (optional)
    private static final String username = "ПОЧТА@bk.ru"; // Mail sender (required)
    private static final String password = "ПАРОЛЬ"; // Sender password (required)
    private static final String filepath = "D:\\hi.png"; // Path to attachment (optional)

    public static void main(String[] args) throws ArrayIndexOutOfBoundsException {
        String FromMail = "";

        //Any mail, here is for examples:
        mail.add("ПОЧТА@bk.ru");
        //mail.add("MAIL@yandex.ru");
        //mail.add("MAIL@gmail.com);
        //mail.add("MAIL@bk.ru");

        for (int i = 0; i < mail.size(); i++) {
            try {
                FromMail = mail.get(i).split("\\@")[1];
            } catch (Exception ignored) {
            }
            if (FromMail.equals("mail.ru") || FromMail.equals("bk.ru") || FromMail.equals("gmail.com") || FromMail.equals("yandex.ru") ||
                    FromMail.equals("inbox.ru") || FromMail.equals("list.ru") || FromMail.equals("yahoo.com") || FromMail.equals("hotmail.com") || FromMail.equals("outlook.com")) {
                Properties properties = new Properties();
                properties.put("mail.smtp.host", "smtp.mail.ru");
                properties.put("mail.smtp.socketFactory.port", "465");
                properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                properties.put("mail.smtp.auth", "true");
                properties.put("mail.smtp.port", "465");
                send(properties, i, filepath);
            }
        }
    }

    private static void send(Properties properties, int i, String filepath) {
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.get(i)));
            message.setSubject(themes);

            File file = new File(filepath);
            if (file.exists() == true) {
                MimeMultipart multipart = new MimeMultipart();
                MimeBodyPart part1 = new MimeBodyPart();
                part1.addHeader("Content-Type", "text/plain; charset=UTF-8");
                part1.setDataHandler(new DataHandler(text, "text/plain; charset=\"utf-8\""));
                multipart.addBodyPart(part1);

                MimeBodyPart part2 = new MimeBodyPart();
                part2.setFileName(MimeUtility.encodeWord(file.getName()));
                part2.setDataHandler(new DataHandler(new FileDataSource(file)));
                multipart.addBodyPart(part2);
                message.setContent(multipart);
                Transport.send(message);
            } else {
                message.setText(text);
                Transport.send(message);
            }
        } catch (Exception ignored) {
        }
    }
}
