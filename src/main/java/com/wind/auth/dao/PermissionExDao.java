package com.wind.auth.dao;

import com.wind.annotation.DAO;
import com.wind.auth.model.Permission;
import com.wind.auth.model.RoleVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * PermissionExDao
 *
 * @author qianchun
 * @date 2019/3/12
 **/
@DAO(catalog = "auth")
public interface PermissionExDao extends PermissionDao {

    /**
     * 根据角色id获取权限列表
     * @param roleId 角色id
     * @return 返回结果
     */
    @Select(SELECT_SQL + " where id in (select permission_id from link_role_permission where role_id = #{roleId})")
    List<Permission> getByRoleId(Long roleId);

    /**
     * 根据roleIds 获取 permission 列表
     * 
     * @param roleIds 角色ids
     * @return 返回结果
     */
    @Select("<script>" + SELECT_SQL + " WHERE id in ( select permission_id from link_role_permission where role_id in "
            + "<foreach collection='roleIds' item='id' open='(' separator=',' close=')'>" + "#{id}" + "</foreach>)"
            + "</script>")
    List<Permission> getByRoleIds(@Param("roleIds") List<Long> roleIds);
}
