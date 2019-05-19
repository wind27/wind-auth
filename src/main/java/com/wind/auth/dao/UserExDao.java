package com.wind.auth.dao;

import com.wind.annotation.DAO;
import com.wind.auth.model.Role;
import com.wind.auth.model.User;
import com.wind.auth.model.UserVO;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * PermissionExDao
 *
 * @author qianchun
 * @date 2019/3/12
 **/
@DAO(catalog = "auth")
public interface UserExDao extends UserDao {

    String COLLOMN_NO_PASSWORD = "u.id, u.username, u.realname, u.salt, u.status, u.create_time, u.update_time, u.mobile, u.idcard, u.email";

    /**
     * 根据手机号获取用户信息
     * @param mobile 手机号
     * @return 返回结果
     */
    @Select(SELECT_SQL + " WHERE mobile = #{mobile} limit 0, 1")
    User findByMobile(String mobile);

    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return 返回结果
     */
    @Select(SELECT_SQL + " WHERE username = #{username} limit 0, 1")
    User findByUsername(String username);

    /**
     * 分页查询
     * @param params 参数
     * @return 返回结果
     */
    @SelectProvider(type = UserProvider.class, method = "findPage")
    List<User> findPage(Map<String, Object> params);



    String selectUserRole = " select " + COLLOMN_NO_PASSWORD + " , ur.role_id as role_id from user u  "
            + "left join link_user_role ur on u.id = ur.user_id  " + "left JOIN role r on ur.role_id = r.id  ";

    /**
     * 根据用户 id 获取 UserVO
     * @param id 用户id
     * @return 返回结果
     */
    @Select(selectUserRole + "  where u.id = #{id}")
    @Results({
            @Result(property = "roleList", column = "role_id", javaType = List.class, many = @Many(select = "com.wind.auth.dao.RoleExDao.findVOById")) })
    List<UserVO> findVOById(Long id);
}
