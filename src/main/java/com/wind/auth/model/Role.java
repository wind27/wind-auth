package com.wind.auth.model;

import com.wind.auth.model.Permission;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Role
 *
 * @author qianchun 17/7/24
 **/
public class Role implements Serializable{
    private long id;
    private String name;
    private int status;
    private Date createTime;
    private Date updateTime;
    private List<Permission> permissions;


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

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
