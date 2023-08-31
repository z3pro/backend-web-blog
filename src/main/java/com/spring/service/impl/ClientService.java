package com.spring.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dto.DataMailDTO;
import com.spring.entity.UserEntity;
import com.spring.entity.VerificationEntity;
import com.spring.payload.request.SignupRequest;
import com.spring.service.IClientService;
import com.spring.utils.DataUtils;

import jakarta.mail.MessagingException;

@Service
public class ClientService implements IClientService {
    @Autowired
    private MailService mailService;
    @Autowired
    private VerificationService verificationService;
    @Autowired
    private UserService userService;

    @Override
    public Boolean create(SignupRequest signupRequest) {
        try {
            // Create verification save database
            String verificationToken = DataUtils.generateTempPwd(6);
            UserEntity user = userService.findByUserName(signupRequest.getUserName());
            verificationService.saveVerification(new VerificationEntity(verificationToken, user));
            // Send mail
            DataMailDTO dataMail = new DataMailDTO();
            dataMail.setTo(signupRequest.getEmail());
            dataMail.setSubject("Xác Nhận Đăng Ký Tài Khoản Web Blog.");

            Map<String, Object> props = new HashMap<>();
            props.put("username", signupRequest.getUserName());
            props.put("verification", verificationToken);
            dataMail.setProps(props);

            mailService.sendHtmlMail(dataMail);
            return true;
        } catch (MessagingException exp) {
            exp.printStackTrace();
        }
        return false;
    }

}
