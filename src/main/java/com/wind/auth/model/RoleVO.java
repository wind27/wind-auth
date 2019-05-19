package com.wind.auth.model;

import java.util.List;

/**
 * RoleVO
 *
 * @author qianchun
 * @date 2019/3/18
 **/
public class RoleVO extends Role{

    private List<Permission> permissionList;

    public List<Permission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }
}
