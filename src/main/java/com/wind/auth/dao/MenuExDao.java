package com.wind.auth.dao;

import com.wind.annotation.DAO;
import com.wind.auth.model.Menu;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * RoleExDao
 *
 * @author qianchun
 * @date 2019/3/12
 **/
@DAO(catalog = "auth")
public interface MenuExDao extends MenuDao {

    /**
     * 分页查询
     *
     * @param params 参数
     * @return 返回结果
     */
    @SelectProvider(type = MenuProvider.class, method = "findPage")
    List<Menu> findPage(Map<String, Object> params);


    /**
     * 根据appId查询 menu列表
     * @param appId appId
     * @return 返回结果
     */
    @Select("select " + COLLOMN + " from menu where status = 1 and app_id = #{appId} ")
    List<Menu> findByAppId(Long appId);


    /**
     * 根据URL查询 menu
     * @param url url
     * @return 返回结果
     */
    @Select("select " + COLLOMN + " from menu where status = 1 and url = #{url} limit 0, 1")
    Menu findByUrl(String url);

}
