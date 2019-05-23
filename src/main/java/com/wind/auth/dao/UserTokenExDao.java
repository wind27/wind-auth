package com.wind.auth.dao;

import com.wind.annotation.DAO;
import com.wind.auth.model.UserToken;
import org.apache.ibatis.annotations.Select;

/**
 * UserTokenExDao
 *
 * @author qianchun
 * @date 2019/3/12
 **/
@DAO(catalog = "auth")
public interface UserTokenExDao extends UserTokenDao {
    /**
     * 获取角色,权限
     *
     * @param username 用户名
     * @return 返回结果
     */
    @Select("select "+COLLOMN+" from "+TABLE_NAME+" where username = #{username} order by id desc")
    UserToken getLastestByUsername(String username);

}
