package com.mail.mailservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class MailController {
    @Autowired
    private MailService mailService;

    @Autowired
    private ExampleService exampleService;

    @GetMapping("/send")
    public MailTo sendTestMail(String email) {
        MailTo mailTO = new MailTo();

        mailTO.setAddress(email);
        mailTO.setTitle("Terry 님이 발송한 이메일입니다.");
        mailTO.setMessage("안녕하세요. 반가워요!");

        mailService.sendMail(mailTO);

        return mailTO;
    }

    @GetMapping("/send2")
    public MailTo sendTestMail2(String email) {
        MailTo mailTO = new MailTo();

        mailTO.setAddress(email);
        mailTO.setTitle("Terry 님이 발송한 이메일입니다.");

        exampleService.sendMailtoTest(mailTO);

        return mailTO;
    }
}
