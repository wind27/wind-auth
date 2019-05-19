package com.wind.auth.model;

import java.util.Date;

/**
 * 请求日志实体 LoggerEntity
 *
 * @author qianchun
 * @date 2019/5/9
 **/
public class LoggerEntity {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 客户端IP地址
     */
    private String clientIp;

    /**
     * sessionId
     */
    private String sessionId;

    /**
     * 请求地址
     */
    private String uri;

    /**
     * 请求类型:ajax, 或者同步
     */
    private String type;

    /**
     * 请求方法方式:GET, POST, DELETE, HEADER, PUT, TRACE
     */
    private String method;

    /**
     * 请求参数
     */
    private String paramData;

    /**
     * 请求时间
     */
    private Date requestTime;

    /**
     * 返回时间
     */
    private Date returnTime;

    /**
     * 请求返回内容
     */
    private String returnData;

    /**
     * 请求状态
     */
    private Integer httpStatusCode;

    /**
     * 耗时
     */
    private Integer timeConsuming;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParamData() {
        return paramData;
    }

    public void setParamData(String paramData) {
        this.paramData = paramData;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    public String getReturnData() {
        return returnData;
    }

    public void setReturnData(String returnData) {
        this.returnData = returnData;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public Integer getTimeConsuming() {
        return timeConsuming;
    }

    public void setTimeConsuming(Integer timeConsuming) {
        this.timeConsuming = timeConsuming;
    }
}
