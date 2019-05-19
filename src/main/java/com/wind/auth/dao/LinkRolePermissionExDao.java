package com.wind.auth.dao;

import com.wind.annotation.DAO;
import com.wind.auth.model.LinkRolePermission;
import com.wind.auth.model.Permission;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * LinkRolePermissionExDao
 *
 * @author qianchun
 * @date 2019/3/12
 **/
@DAO(catalog = "auth")
public interface LinkRolePermissionExDao extends LinkRolePermissionDao {

    /**
     * 批量新增
     */
    @Insert("<script>"
            + "INSERT INTO " + TABLE_NAME + "(role_id, permission_id, create_time, update_time) VALUES "
            + "<foreach collection='list' item='item' index='index'  separator=','>"
            + "(#{item.roleId}, #{item.permissionId}, now(), now())"
            + "</foreach>"
            + "</script>")
    int batchSave(List<LinkRolePermission> list);

    /**
     * 根据 permissionIds 删除 link_role_permission
     *
     * @param permissionIds 权限id列表
     * @return 返回操作结果
     */
    @Delete("<script>" + "DELETE FROM link_role_permission WHERE permission_id in "
            + "<foreach collection='permissionIds' item='id' open='(' separator=',' close=')'>" + "#{id}" + "</foreach>"
            + "</script>")
    int deleteByPermissionId(@Param("permissionIds") List<Long> permissionIds);

    /**
     * 根据 roleId 删除 link_role_permission
     * 
     * @param roleId 角色id
     * @return
     */
    @Delete("DELETE FROM " + TABLE_NAME + " WHERE role_id = #{roleId} ")
    int deleteByRoleId(Long roleId);

}
