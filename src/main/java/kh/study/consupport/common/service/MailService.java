package kh.study.consupport.common.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
     
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(String toUser) {
    	
    	System.out.println("send!!");
    	System.out.println("send!!");
    	System.out.println("send!!");
    	System.out.println("send!!");
    	System.out.println("send!!");
    	
    	
        
        // 수신 대상을 담을 ArrayList 생성
        ArrayList<String> toUserList = new ArrayList<>();
        
        // 수신 대상 추가
        toUserList.add(toUser);
        
        // 수신 대상 개수
        int toUserSize = toUserList.size();
        
        // SimpleMailMessage (단순 텍스트 구성 메일 메시지 생성할 때 이용)
        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        
        // 수신자 설정
        simpleMessage.setTo((String[]) toUserList.toArray(new String[toUserSize]));
        
        // 메일 제목
        simpleMessage.setSubject("Subject Sample");
        
        // 메일 내용
        simpleMessage.setText("Text Sample");
        
        // 메일 발송
        javaMailSender.send(simpleMessage);
    }
}