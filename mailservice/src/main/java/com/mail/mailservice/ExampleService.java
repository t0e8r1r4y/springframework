package com.mail.mailservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExampleService {

    private static final String EXAMPLE_LINK_TEMPLATE = "mail/mail";

    private final TemplateEngine templateEngine;

    private final MailService mailService;

    public void sendMailtoTest( MailTo mail) {
        Context context = getContext("Terry");
        String message = templateEngine.process(EXAMPLE_LINK_TEMPLATE, context);

        mail.setMessage(message);
        mailService.sendMailHtml(mail);
    }

    private Context getContext(String name) {

        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("message", "메일 메시지");
        return context;
    }

}
