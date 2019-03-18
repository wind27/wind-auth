package com.wind.auth.dao;

import com.wind.annotation.DAO;
import com.wind.auth.model.User;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

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
    @Select(SELECT_SQL + " WHERE mobile = #{mobile} limit 0, 1")
    User findByMobile(String mobile);

    @SelectProvider(type = UserProvider.class, method = "findPage")
    List<User> findPage(Map<String, Object> params);
}
