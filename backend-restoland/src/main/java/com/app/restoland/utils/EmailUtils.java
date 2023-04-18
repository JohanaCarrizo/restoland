package com.app.restoland.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
public class EmailUtils {

    @Autowired
    private JavaMailSender emailSender;
    private String emailAdmin = "jtest.institucional@gmail.com";

    public void sendSimpleMessage(String to, String subject, String text){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailAdmin);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text.concat(emailAdmin));
        emailSender.send(message);
    }

    public void forgotMail(String to, String subject, String password) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(emailAdmin);
        helper.setTo(to);
        helper.setSubject(subject);
        String htmlMsg = "<p><b>Sus credenciales para el inicio de session a RESTOLAND</b><br><b>Email: </b> "
                + to + " <br><b>Contraseña: </b> " + password
                + "<br><a href=\"http://localhost:4200/\">Click aquí para iniciar session</a></p>";
        message.setContent(htmlMsg, "text/html");
        emailSender.send(message);
    }
/*
    private String[] getCcArray(List<String> ccList){
        String[] cc = new String[ccList.size()];
        for(int i = 0; i < ccList.size(); i++){
            cc[i] = ccList.get(i);
        }
        return cc;
    }*/
}
