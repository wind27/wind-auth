package com.wind.auth.service;

import com.wind.auth.dao.MenuExDao;
import com.wind.auth.model.Menu;
import com.wind.common.ErrorCode;
import com.wind.utils.JsonResponseUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * MenuService
 *
 * @author qianchun 2019/1/10
 **/
@Service
public class MenuService {

    @Autowired
    private MenuExDao menuExDao;

    /**
     * 主键id查询
     * 
     * @param id id
     * @return 返回结果
     */
    public Menu findById(Long id) {
        if (id == 0) {
            return null;
        }
        return menuExDao.getByPrimary(id);
    }

    /**
     * appId 查询菜单
     * 
     * @param appId app id
     * @return 返回结果
     */
    public List<Menu> findByAppId(Long appId) {
        if (appId == null) {
            return null;
        }
        return menuExDao.findByAppId(appId);
    }

    /**
     * URL 查询
     * 
     * @param url URL
     * @return 返回结果
     */
    public Menu findByUrl(String url) {
        if (StringUtils.isEmpty(url)) {
            return null;
        }
        return menuExDao.findByUrl(url);
    }

    /**
     * 校验URL是否可用
     * 
     * @param url URL
     * @return 返回结果
     */
    public boolean checkUrlExists(String url) {
        if (StringUtils.isEmpty(url)) {
            return false;
        }
        return menuExDao.findByUrl(url) == null;
    }

    /**
     * 新增或修改
     * 
     * @param menu menu
     * @return 返回结果
     */
    public synchronized ErrorCode saveOrUpdate(Menu menu) {
        if(menu == null) {
            return ErrorCode.PARAM_ERROR;
        }
         Menu current = this.findByUrl(menu.getUrl());
        if (menu.getId() != null && current==null) {
            return ErrorCode.MENU_URL_HAS_EXISTS;
        }
        current.setStatus(1);
        current.setUrl(menu.getUrl());
        current.setName(menu.getName());
        current.setAppId(menu.getAppId());
        current.setUpdateTime(new Date());
        if (current.getId() != null) {
            menuExDao.update(current);
        } else {
            menuExDao.save(current);
        }
        return ErrorCode.SUCCESS;
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
        if (menuExDao.getByPrimary(id) == null) {
            return true;
        }
        return menuExDao.delete(id) > 0;
    }

    /**
     * 统计
     * 
     * @param params 参数
     * @return 返回结果
     */
    public int count(Map<String, Object> params) {
        return menuExDao.count(params);
    }

    /**
     * 分页
     * 
     * @param params 参数
     * @return 返回结果
     */
    public List<Menu> findPage(Map<String, Object> params) {
        return menuExDao.findPage(params);
    }
}
