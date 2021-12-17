package com.doghandeveloper.doggu.common.util;

import com.doghandeveloper.doggu.account.dto.response.EmailAuthenticationResponse;
import com.doghandeveloper.doggu.common.config.EmailProperties;
import com.doghandeveloper.doggu.common.exception.AuthException;
import com.doghandeveloper.doggu.common.exception.EmailSendException;
import com.doghandeveloper.doggu.common.exception.ErrorCode;
import com.doghandeveloper.doggu.common.exception.GlobalExceptionHandler;
import com.doghandeveloper.doggu.common.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;
@RequiredArgsConstructor
@Service
public class EmailSendUtil {

    private final RedisUtil redisUtil;
    private final EmailProperties emailProperties;
    private final JavaMailSender javaMailSender;

    public EmailAuthenticationResponse sendEmailAuthenticationCode(String email){

        String key = createCode();
        EmailAuthenticationResponse emailAuthenticationResponse = new EmailAuthenticationResponse(key);
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            
            message.setTo(email);
            message.setSubject("doggu 이메일 인증 번호 입니다!");
            message.setText("인증 번호 : "+key);
            javaMailSender.send(message);
        } catch (Exception e){
            throw new EmailSendException(ErrorCode.EMAIL_SEND_FAIL_ERROR);
        }

        redisUtil.setDataExpire(key, email, emailProperties.getValidTime());
        return emailAuthenticationResponse;
    }

    public boolean getUserEmailByCode(String code) {
        String email = redisUtil.getData(code);
        if (email == null) {
            throw new AuthException(ErrorCode.AUTHENTICATION_ERROR);
        }
        return true;
    }

    private String createCode() {
        Random random=new Random();
        String key="";
        for(int i =0; i<8;i++) {
            int index=random.nextInt(25)+65; //A~Z까지 랜덤 알파벳 생성
            key+=(char)index;
        }
        int numIndex=random.nextInt(99999999)+1000; //8자리 랜덤 정수를 생성
        key+=numIndex;
        return key;
    }
}
