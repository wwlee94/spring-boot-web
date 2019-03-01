package com.springboot.web.login.register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailService {

    @Autowired
    JavaMailSenderImpl sender;

    //TODO: 메일 이름 바꾸기
    public void sendMail(String email){

        String host ="http://localhost:8080/member/";
        String content = "다음 링크에 접속 하여 이메일 인증을 진행 하세요."+
                "<a href='"+host+"emailCheck/"+email+"'>메일 인증</a>";

        MimeMessage message = sender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            helper.setFrom("test@gmail.com");
            helper.setTo(email);
            helper.setSubject("Send mail from Spring Online Judge");
            helper.setText("<h1>메일 인증</h1>"+content,true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        sender.send(message);
    }
}
