package com.wind.auth.service;

import com.wind.auth.dao.PermissionExDao;
import com.wind.auth.dao.UserExDao;
import com.wind.auth.model.Permission;
import com.wind.auth.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        if (permission == null || permission.getId() != null) {
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
}
