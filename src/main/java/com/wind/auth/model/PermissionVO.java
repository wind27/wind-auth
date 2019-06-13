package com.wind.auth.model;

import java.util.List;

/**
 * PermissionVO
 *
 * @author qianchun
 * @date 2019/3/18
 **/
public class PermissionVO {

    /**
     * 子权限列表
     */
    private List<PermissionVO> children;

    /**
     * 父权限
     */
    private Permission parent;

    /**
     * 当前权限
     */
    private Permission current;

    /**
     * 是否有权限
     */
    private int hasPermission = 0;

    public Permission getParent() {
        return parent;
    }

    public void setParent(Permission parent) {
        this.parent = parent;
    }

    public List<PermissionVO> getChildren() {
        return children;
    }

    public void setChildren(List<PermissionVO> children) {
        this.children = children;
    }

    public Permission getCurrent() {
        return current;
    }

    public void setCurrent(Permission current) {
        this.current = current;
    }

    public int getHasPermission() {
        return hasPermission;
    }

    public void setHasPermission(int hasPermission) {
        this.hasPermission = hasPermission;
    }
}
