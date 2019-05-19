package com.wind.auth.dao;

import com.wind.annotation.DAO;
import com.wind.auth.model.LinkUserRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

/**
 * LinkUserRoleExDao
 *
 * @author qianchun
 * @date 2019/3/12
 **/
@DAO(catalog = "auth")
public interface LinkUserRoleExDao extends LinkUserRoleDao {

    /**
     * 批量新增
     */
    @Insert("<script>" + "INSERT INTO " + TABLE_NAME + "(user_id, role_id, create_time, update_time) VALUES "
            + "<foreach collection='list' item='item' index='index' separator=','>"
            + "(#{item.userId}, #{item.roleId}, now(), now())" + "</foreach>" + "</script>")
    int batchSave(List<LinkUserRole> list);

    /**
     * 根据 roleIds 删除 link_user_role
     *
     * @param roleIds 角色id列表
     * @return 返回操作结果
     */
    @Delete("<script>" + "DELETE FROM " + TABLE_NAME + " WHERE role_id in "
            + "<foreach collection='list' item='item' open='(' separator=',' close=')'>" + "#{item}" + "</foreach>"
            + "</script>")
    int deleteByRoleIds(List<Long> roleIds);

    /**
     * 根据 userId 删除 link_user_role
     * 
     * @param userId 用户id
     * @return 返回结果
     */
    @Delete("DELETE FROM " + TABLE_NAME + " WHERE user_id = #{userId} ")
    int deleteByRoleId(Long userId);

}
