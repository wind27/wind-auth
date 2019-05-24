package com.wind.auth.service;

import com.wind.auth.dao.LinkUserRoleExDao;
import com.wind.auth.dao.PermissionExDao;
import com.wind.auth.dao.RoleExDao;
import com.wind.auth.dao.UserExDao;
import com.wind.auth.model.*;
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
public class UserService {
    private final static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserExDao userExDao;

    @Autowired
    private PermissionExDao permissionExDao;

    @Autowired
    private RoleExDao roleExDao;

    @Autowired
    private LinkUserRoleExDao linkUserRoleExDao;

    /**
     * 主键id查询
     * 
     * @param id id
     * @return 返回结果
     */
    public User findById(Long id) {
        if (id == 0) {
            return null;
        }
        return userExDao.getByPrimary(id);
    }

    /**
     * 手机号查询
     * 
     * @param mobile 手机号
     * @return 返回结果
     */
    public User findByMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return null;
        }
        return userExDao.findByMobile(mobile);
    }

    public User findByUsername(String username) {
        if (StringUtils.isEmpty(username)) {
            return null;
        }
        return userExDao.findByUsername(username);
    }


    /**
     * 新增或修改
     * 
     * @param user user
     * @return 返回结果
     */
    public boolean saveOrUpdate(User user) {
        if (user == null) {
            return false;
        }
        if (user.getId() != null) {
            return userExDao.update(user) > 0;
        } else {
            return userExDao.save(user) > 0;
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
        if (userExDao.getByPrimary(id) == null) {
            return true;
        }
        return userExDao.delete(id) > 0;
    }

    /**
     * 统计
     * 
     * @param params 参数
     * @return 返回结果
     */
    public int count(Map<String, Object> params) {
        return userExDao.count(params);
    }

    /**
     * 分页
     * 
     * @param params 参数
     * @return 返回结果
     */
    public List<User> findPage(Map<String, Object> params) {
        return userExDao.findPage(params);
    }


    /**
     * 根据 userId 获取权限列表
     * @param userId 用户id
     * @return 返回结果
     */
    public List<Permission> findPermissionByUserId(Long userId) {
        if(userId == null) {
            return null;
        }
        List<Long> roleIdList = roleExDao.findRoleIdsByUserId(userId);
        if(CollectionUtils.isNotEmpty(roleIdList)) {
            return permissionExDao.getByRoleIds(roleIdList);
        }
        return  null;
    }
    /**
     * 根据用户id获取用户信息,角色信息,权限信息
     * @param id 用户id
     * @return 返回结果
     */
    public List<UserVO> findVOById(Long id) {
        if(id == null) {
            return null;
        }
        return userExDao.findVOById(id);
    }



    /**
     * 更新用户绑定角色
     * @param userId 用户id
     * @param roleIds 权限列表
     * @return 返回结果
     */
//    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public ErrorCode updateRolesById(Long userId, List<Long> roleIds) {
        if(userId == null) {
            return ErrorCode.PARAM_ERROR;
        }

        User user = this.findById(userId);
        if (user == null) {
            logger.error("用户绑定角色更新, 用户不存在");
            return ErrorCode.AUTH_USER_NOT_EXISTS;
        }

        List<Long> deleteRoleIds = new ArrayList<>();
        List<Long> currentRoleIdList = roleExDao.findRoleIdsByUserId(userId);
        //删除角色
        if(CollectionUtils.isNotEmpty(currentRoleIdList)) {
            currentRoleIdList.forEach(id -> {
                if(!roleIds.contains(id)) {
                    deleteRoleIds.add(id);
                }
            });

            if(CollectionUtils.isNotEmpty(deleteRoleIds)) {
                linkUserRoleExDao.deleteByRoleIds(deleteRoleIds);
            }
        }
        List<LinkUserRole> addLinkUserRoleList = new ArrayList<>();
        //新增角色
        if(CollectionUtils.isNotEmpty(roleIds)) {
            roleIds.forEach(roleId -> {
                if(!currentRoleIdList.contains(roleId)) {
                    Date now = new Date();
                    LinkUserRole userRole = new LinkUserRole();
                    userRole.setRoleId(roleId);
                    userRole.setUserId(userId);
                    userRole.setCreateTime(now);
                    userRole.setUpdateTime(now);
                    addLinkUserRoleList.add(userRole);
                }
            });
            if(CollectionUtils.isNotEmpty(addLinkUserRoleList)) {
                linkUserRoleExDao.batchSave(addLinkUserRoleList);
            }
        }
        return ErrorCode.SUCCESS;
    }
}
