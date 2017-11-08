package com.wind.auth.model;

import com.wind.model.BaseObject;

import java.util.Date;

/**
 * LinkUserRole 用户角色关联关系
 *
 * @author qianchun 17/7/24
 **/
public class LinkUserRole extends BaseObject {

    /**
     * 主键ID
     */
    private long id;

    /**
     * 用户ID
     */
    private long userId;

    /**
     * 角色ID
     */
    private long roleId;

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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
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
