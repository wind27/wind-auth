package com.wind.auth.model;

import java.util.ArrayList;

/**
 * TestMail
 *
 * @author qianchun
 * @date 2019/4/26
 **/
public class TestMail {

    public static void main(String[] args) throws Exception {
        new MailSender().title("测试SpringBoot发送邮件").content("简单文本内容发送").contentType(EMailContentType.TEXT)
                .targets(new ArrayList<String>() {
                    {
                        add("qianchun@huli.com");
                    }
                }).send();
    }
}
