package com.wind.auth.model;

import java.util.ArrayList;
import java.util.List;

/**
 * EMail
 *
 * @author qianchun
 * @date 2019/3/18
 **/
public class EMail {

    /**
     * SMTP服务器
     */
    private String smtpService = "smtp.qq.com";

    /**
     * SMTP端口
     */
    private String smtpPort = "587";

    /**
     * 发送方邮箱
     */
    private String fromEmailAddress = "1056272517@qq.com";


    private String fromEmailNickname = "游风";

    /**
     * 发送方邮箱 SMTP 授权码
     * ukmp lfta gewp bgad
     * ukmplftagewpbgad
     */
    private String fromEmailSmtpPwd = "ukmplftagewpbgad";

    /**
     * 邮件标题
     */
    private String title = "test";

    /**
     * 邮件内容
     */
    private String content = "test";

    /**
     * 内容格式(默认HTML)
     */
    private String contentType = "text";

    /**
     * 接收方
     */
    private List<String> receiverList = new ArrayList<>();

    public String getSmtpService() {
        return smtpService;
    }

    public void setSmtpService(String smtpService) {
        this.smtpService = smtpService;
    }

    public String getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(String smtpPort) {
        this.smtpPort = smtpPort;
    }

    public String getFromEmailAddress() {
        return fromEmailAddress;
    }

    public void setFromEmailAddress(String fromEmailAddress) {
        this.fromEmailAddress = fromEmailAddress;
    }

    public String getFromEmailSmtpPwd() {
        return fromEmailSmtpPwd;
    }

    public void setFromEmailSmtpPwd(String fromEmailSmtpPwd) {
        this.fromEmailSmtpPwd = fromEmailSmtpPwd;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public List<String> getReceiverList() {
        return receiverList;
    }

    public void setReceiverList(List<String> receiverList) {
        this.receiverList = receiverList;
    }

    public String getFromEmailNickname() {
        return fromEmailNickname;
    }

    public void setFromEmailNickname(String fromEmailNickname) {
        this.fromEmailNickname = fromEmailNickname;
    }
}
