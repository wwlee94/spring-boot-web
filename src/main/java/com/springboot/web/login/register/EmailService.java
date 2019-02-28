package com.springboot.web.login.register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class EmailService {

    @Autowired
    private MailSender sender;

    public void sendMail(String email){

        String host ="http://localhost:8080/member/";
        String content = "다음 링크에 접속 하여 이메일 인증을 진행 하세요."+"<a href='"+host+"emailCheck'>메일 인증</a>";
        SimpleMailMessage msg = new SimpleMailMessage();

        //TODO: 메일 이름 바꾸기
        msg.setFrom("test@mail.com");
//        msg.setTo(email);
        msg.setTo("tjdds1109@naver.com");
        msg.setSubject("Send mail from Spring Online Judge");
        msg.setText(content);

        this.sender.send(msg);
    }
}
