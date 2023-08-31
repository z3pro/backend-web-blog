package com.spring.service;

import com.spring.dto.DataMailDTO;

import jakarta.mail.MessagingException;

public interface IMailService {
    void sendHtmlMail(DataMailDTO dataMail) throws MessagingException;
}