package com.wind.auth.model;

import java.util.List;

/**
 * UserVO
 *
 * @author qianchun
 * @date 2019/3/18
 **/
public class UserVO extends User {
    private List<Role> roleList;

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }
}
