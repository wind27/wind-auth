package com.wind.auth.dao;

import com.wind.annotation.DAO;
import com.wind.auth.model.Role;
import com.wind.auth.model.RoleVO;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * RoleExDao
 *
 * @author qianchun
 * @date 2019/3/12
 **/
@DAO(catalog = "auth")
public interface AppExDao extends AppDao {
//
//    /**
//     * 分页查询
//     *
//     * @param params 参数
//     * @return 返回结果
//     */
//    @SelectProvider(type = RoleProvider.class, method = "findPage")
//    List<Role> findPage(Map<String, Object> params);
//
//    /**
//     * 根据姓名统计
//     *
//     * @param name 姓名
//     * @return 返回结果
//     */
//    @Select("SELECT COUNT(1) FROM role where name = #{name} ")
//    int countByName(String name);
//
//    /**
//     * 获取角色,权限
//     *
//     * @param id 角色id
//     * @return 返回结果
//     */
//    @Select("select * from role where id = #{id}")
//    @Results({
//            @Result(property = "permissionList", column = "id", javaType = List.class, many = @Many(select = "com.wind.auth.dao.PermissionExDao.findByRoleId")) })
//    List<RoleVO> findVOById(Long id);

}
