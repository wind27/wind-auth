package com.wind.auth.model;

import com.wind.model.BaseObject;

import java.util.Date;

/**
 * LinkRolePermission 角色权限关联关系
 *
 * @author qianchun 17/7/24
 **/
public class LinkRolePermission extends BaseObject {
    /**
     * 主键ID
     */
    private long id;

    /**
     * 角色ID
     */
    private long roleId;

    /**
     * 权限ID
     */
    private long permissionId;

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

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(long permissionId) {
        this.permissionId = permissionId;
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
