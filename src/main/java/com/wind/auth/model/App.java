package com.wind.auth.model;

import com.wind.model.BaseObject;

import java.util.Date;

/**
 * App 应用app
 *
 * @author qianchun 17/11/6
 **/
public class App extends BaseObject {

    /**
     * 主键ID
     */
    private long id;

    /**
     * 应用名称
     */
    private String name;

    /**
     * 应用状态 1:启用 0:停用
     */
    private int status;

    /**
     * 首页地址
     */
    private String homePageUrl;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getHomePageUrl() {
        return homePageUrl;
    }

    public void setHomePageUrl(String homePageUrl) {
        this.homePageUrl = homePageUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
