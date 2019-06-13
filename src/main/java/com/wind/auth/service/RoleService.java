package com.wind.auth.service;

import com.wind.auth.dao.LinkRolePermissionExDao;
import com.wind.auth.dao.PermissionExDao;
import com.wind.auth.dao.RoleExDao;
import com.wind.auth.dao.UserExDao;
import com.wind.auth.model.LinkRolePermission;
import com.wind.auth.model.Permission;
import com.wind.auth.model.Role;
import com.wind.common.ErrorCode;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * UserService
 *
 * @author qianchun 2019/1/10
 **/
@Service
public class RoleService {
    private final static Logger logger = LoggerFactory.getLogger(RoleService.class);

    @Autowired
    private UserExDao userExDao;

    @Autowired
    private RoleExDao roleExDao;

    @Autowired
    private PermissionExDao permissionExDao;

    @Autowired
    private LinkRolePermissionExDao linkRolePermissionExDao;

    /**
     * 主键id查询
     * 
     * @param id id
     * @return 返回结果
     */
    public Role findById(Long id) {
        if (id == 0) {
            return null;
        }
        return roleExDao.getByPrimary(id);
    }

    /**
     * 根据用户id获取角色列表
     * @param id 用户id
     * @return 返回结果
     */
    public List<Role> findRoleByUserId(Long id) {
        if (id == 0) {
            return null;
        }
        return roleExDao.findRoleByUserId(id);
    }

    /**
     * 角色名是否可用
     * 
     * @param name 角色名
     * @return 返回结果
     */
    public boolean canUseRoleName(String name) {
        if (StringUtils.isEmpty(name)) {
            return false;
        }
        return roleExDao.countByName(name) == 0;
    }

    /**
     * 新增或修改
     * 
     * @param role role
     * @return 返回结果
     */
    public boolean saveOrUpdate(Role role) {
        if (role == null) {
            return false;
        }
        if (role.getId() != null) {
            return roleExDao.update(role) > 0;
        } else {
            return roleExDao.save(role) > 0;
        }
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
        if (roleExDao.getByPrimary(id) == null) {
            return true;
        }
        return roleExDao.delete(id) > 0;
    }

    /**
     * 统计
     * 
     * @param params 参数
     * @return 返回结果
     */
    public int count(Map<String, Object> params) {
        return roleExDao.count(params);
    }

    /**
     * 分页
     * 
     * @param params 参数
     * @return 返回结果
     */
    public List<Role> findPage(Map<String, Object> params) {
        return roleExDao.findPage(params);
    }

    /**
     * 根据角色id获取权限列表
     * @param roleId 角色id
     * @return 返回结果
     */
    public List<Permission> getPermissionByRoleId(Long roleId) {
        if(roleId == null) {
            return null;
        }
        return permissionExDao.getByRoleId(roleId);
    }


    /**
     * 更新角色权限
     * @param roleId 角色id
     * @param permissionIdList 权限列表
     * @return 返回结果
     */
//    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public ErrorCode updatePermission(Long roleId, List<Long> permissionIdList) {
        if(roleId == null) {
            return ErrorCode.PARAM_ERROR;
        }

        Role role = this.findById(roleId);
        if (role == null) {
            logger.error("角色权限更新, 角色不存在");
            return ErrorCode.AUTH_ROLE_NOT_EXISTS;
        }

        List<Long> deletePermissionIds = new ArrayList<>();
        List<Long> currentPermissionId = roleExDao.findPermissionIdByRoleId(roleId);
        //获取要删除的权限
        if(CollectionUtils.isNotEmpty(currentPermissionId)) {
            currentPermissionId.forEach(id -> {
                if(!permissionIdList.contains(id)) {
                    deletePermissionIds.add(id);
                }
            });

            if(CollectionUtils.isNotEmpty(deletePermissionIds)) {
                linkRolePermissionExDao.deleteByPermissionId(deletePermissionIds);
            }
        }
        List<LinkRolePermission> addLinkRolePermissionList = new ArrayList<>();
        //新增权限
        if(CollectionUtils.isNotEmpty(permissionIdList)) {
            permissionIdList.forEach(permissionnId -> {
                if(currentPermissionId.contains(permissionnId)) {
                    Date now = new Date();
                    LinkRolePermission rolePermission = new LinkRolePermission();
                    rolePermission.setRoleId(roleId);
                    rolePermission.setPermissionId(permissionnId);
                    rolePermission.setCreateTime(now);
                    rolePermission.setUpdateTime(now);
                    addLinkRolePermissionList.add(rolePermission);
                }
            });
            if(CollectionUtils.isNotEmpty(addLinkRolePermissionList)) {
                linkRolePermissionExDao.batchSave(addLinkRolePermissionList);
            }
        }
        return ErrorCode.SUCCESS;
    }
}
