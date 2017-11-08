package com.wind.auth.model;

import com.wind.model.BaseObject;

import java.util.Date;
import java.util.List;

/**
 * Role 角色表
 *
 * @author qianchun 17/7/24
 **/
public class Role extends BaseObject {
    /**
     * 主键ID
     */
    private long id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 状态 1:启用 0:停用
     */
    private int status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 权限列表
     */
    private List<Permission> permissionList;


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

    public List<Permission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }
}
