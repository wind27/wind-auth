package com.wind.auth.service;

import com.wind.auth.dao.PermissionExDao;
import com.wind.auth.dao.UserExDao;
import com.wind.auth.model.Permission;
import com.wind.auth.model.PermissionVO;
import com.wind.auth.model.User;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UserService
 *
 * @author qianchun 2019/1/10
 **/
@Service
public class PermissionService {

    @Autowired
    private PermissionExDao permissionExDao;

    /**
     * 主键id查询
     *
     * @param id id
     * @return 返回结果
     */
    public Permission getById(Long id) {
        if (id == 0) {
            return null;
        }
        return permissionExDao.getByPrimary(id);
    }

    /**
     * 根据角色ids获取权限
     * @param roleIds 角色ids
     * @return 返回结果
     */
    public List<Permission> getByRoleIds(List<Long> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return null;
        }
        return permissionExDao.getByRoleIds(roleIds);
    }

    /**
     * 主键id查询
     *
     * @param value 权限值
     * @return 返回结果
     */
    public Permission getByValue(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        return permissionExDao.getByValue(value);
    }

    /**
     * 新增或修改
     *
     * @param permission permission
     * @return 返回结果
     */
    public boolean saveOrUpdate(Permission permission) {
        if (permission == null) {
            return false;
        }
        if (permission.getId() != null) {
            return permissionExDao.update(permission) > 0;
        } else {
            return permissionExDao.save(permission) > 0;
        }
    }

    /**
     * 更新
     * 
     * @param permission 权限
     * @return 返回结果
     */
    public boolean update(Permission permission) {
        if (permission == null || permission.getId() == null) {
            return false;
        }
        return permissionExDao.update(permission) > 0;
    }

    /**
     * 删除
     *
     * @param id id
     * @return 返回结果
     */
    public boolean delete(Long id) {
        if (id == null) {
            return true;
        }
        if (permissionExDao.getByPrimary(id) == null) {
            return true;
        }
        return permissionExDao.delete(id) > 0;
    }

    /**
     * 统计
     *
     * @param params 参数
     * @return 返回结果
     */
    public int count(Map<String, Object> params) {
        return permissionExDao.count(params);
    }

    /**
     * 分页
     *
     * @param params 参数
     * @return 返回结果
     */
    public List<Permission> findPage(Map<String, Object> params) {
        return permissionExDao.findPage(params);
    }

    private List<Permission> catalog(List<Permission> list) {
        List<Permission> permissionList = new ArrayList<>();
        if (CollectionUtils.isEmpty(list)) {
            return permissionList;
        }
        return permissionList;
    }

    /**
     * 排序
     * 
     * @param allPermissionList 所有权限列表
     * @param hasPermissionList 拥有权限
     * @return 返回封装后的权限节点
     */
    public PermissionVO sort(List<Permission> allPermissionList, List<Permission> hasPermissionList) {
        PermissionVO root = null;
        if (CollectionUtils.isEmpty(allPermissionList)) {
            return null;
        }
        for (Permission permission : allPermissionList) {
            if (permission == null || permission.getId() == null) {
                continue;
            }
            PermissionVO permissionVO = new PermissionVO();
            permissionVO.setCurrent(permission);
            if (permission.getParentId() == 0) {
                root = permissionVO;
            }
            break;
        }
        setChildren(allPermissionList, hasPermissionList, root);
        return root;
    }

    /**
     * 获取子级权限
     * 
     * @param allPermissionList 全部权限列表
     * @param nodeVO 权限节点
     * @return 返回结果
     */
    private void setChildren(List<Permission> allPermissionList, List<Permission> hasPermissionList,
            PermissionVO nodeVO) {
        if (nodeVO == null || nodeVO.getCurrent() == null || CollectionUtils.isEmpty(allPermissionList)) {
            return;
        }
        List<PermissionVO> children = new ArrayList<>();
        for (Permission permission : allPermissionList) {
            if (permission == null || !permission.getParentId().equals(nodeVO.getCurrent().getId())) {
                continue;
            }
            PermissionVO childVO = new PermissionVO();
            childVO.setCurrent(permission);
            hasPermission(hasPermissionList, childVO);
            setChildren(allPermissionList, hasPermissionList, childVO);
            children.add(childVO);
        }
        nodeVO.setChildren(children);
    }

    /**
     * 是否有权限
     * 
     * @param hasPermissionList 当前拥有权限
     * @param permissionVO 待校验权限
     * @return 返回结果
     */
    private void hasPermission(List<Permission> hasPermissionList, PermissionVO permissionVO) {
        if (permissionVO == null || permissionVO.getCurrent() == null) {
            return;
        }
        if (CollectionUtils.isEmpty(hasPermissionList)) {
            permissionVO.setHasPermission(0);
            return;
        }
        for (Permission item : hasPermissionList) {
            if (item != null && item.getId().equals(permissionVO.getCurrent().getId())) {
                permissionVO.setHasPermission(1);
                return;
            }
        }
        permissionVO.setHasPermission(0);
    }

    // /**
    // * 获取父级权限
    // *
    // * @param list 权限列表
    // * @param node 权限节点
    // * @return 返回结果
    // */
    // private Permission getParent(List<Permission> list, Permission node) {
    // if (node == null || node.getParentId() == 0 || CollectionUtils.isEmpty(list)) {
    // return null;
    // }
    //
    // for (Permission permission : list) {
    // if (permission != null && permission.getId().equals(node.getParentId())) {
    // return permission;
    // }
    // }
    // return null;
    // }
}