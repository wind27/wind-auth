package com.wind.auth.model;

/**
 * EMailContentType
 * 邮件内容格式
 *
 * @author qianchun
 * @date 2019/3/18
 **/
public enum EMailContentType {

    HTML("text/html;charset=UTF-8"),
    TEXT("TEXT");

    private String value;

    EMailContentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
