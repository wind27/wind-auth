package com.wind.auth.controller;

import com.wind.auth.model.Menu;
import com.wind.auth.service.MenuService;
import com.wind.common.ErrorCode;
import com.wind.common.Page;
import com.wind.utils.JsonResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/menu")
public class MenuController {
    private final static Logger logger = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    private MenuService menuService;

    /**
     * 列表
     * 
     * @return 返回结果
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object list(@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) {
        Map<String, Object> data = new HashMap<>();
        try {
            Page pageModel = new Page(pageNo, pageSize);
            Map<String, Object> params = new HashMap<>();
            params.put("page", pageModel);
            List<Menu> list = menuService.findPage(params);
            data.put("list", list);
            return JsonResponseUtil.ok(data);
        } catch (Exception e) {
            logger.error("用户列表, 异常", e);
            return JsonResponseUtil.fail(ErrorCode.SYS_ERROR);
        }
    }

    /**
     * 详情
     *
     * @return 返回结果
     */
    @ResponseBody
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public Object detail(@PathVariable("id") Long id) {
        Map<String, Object> data = new HashMap<>();
        if (id == null) {
            return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
        }
        Menu menu = menuService.findById(id);
        if (menu == null) {
            return JsonResponseUtil.fail(ErrorCode.AUTH_USER_NOT_EXISTS);
        }
        data.put("menu", menu);
        return JsonResponseUtil.ok(data);
    }

    /**
     * 新增/更新
     *
     * @return 返回结果
     */
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public Object add(Menu menu) {
        if (StringUtils.isEmpty(menu.getUrl())) {
            return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
        }
        ErrorCode errorCode = menuService.saveOrUpdate(menu);
        if (ErrorCode.SUCCESS.equals(errorCode)) {
            return JsonResponseUtil.ok();
        } else {
            return JsonResponseUtil.fail(errorCode);
        }
    }

    /**
     * 删除
     *
     * @return 返回结果
     */
    @ResponseBody
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public Object delete(@PathVariable("id") Long id) {
        if(id == null) {
            return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
        }
        boolean flag = menuService.delete(id);
        if(flag) {
            return JsonResponseUtil.ok();
        } else {
            return JsonResponseUtil.fail();
        }
    }

    /**
     * 启用
     *
     * @return 返回结果
     */
    @ResponseBody
    @RequestMapping(value = "/enable/{id}", method = RequestMethod.GET)
    public Object enable(@PathVariable("id") Long id) {
        if (id == null) {
            return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
        }
        Menu menu = menuService.findById(id);
        if (menu == null) {
            return JsonResponseUtil.fail(ErrorCode.AUTH_MENU_NOT_EXISTS);
        }
        menu.setStatus(1);
        menu.setUpdateTime(new Date());
        menuService.saveOrUpdate(menu);
        return JsonResponseUtil.ok(menu);
    }

    /**
     * 停用
     *
     * @return 返回结果
     */
    @ResponseBody
    @RequestMapping(value = "/disable/{id}", method = RequestMethod.GET)
    public Object disable(@PathVariable("id") Long id) {
        if (id == null) {
            return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
        }
        Menu menu = menuService.findById(id);
        if (menu == null) {
            return JsonResponseUtil.fail(ErrorCode.AUTH_MENU_NOT_EXISTS);
        }
        menu.setStatus(0);
        menu.setUpdateTime(new Date());
        menuService.saveOrUpdate(menu);
        return JsonResponseUtil.ok(menu);
    }

//    /**
//     * 统计
//     *
//     * @return 返回结果
//     */
//    @ResponseBody
//    @RequestMapping(value = "/count", method = RequestMethod.GET)
//    public Object count(Menu menu) {
//        Map<String, Object> param = new HashMap<>();
//        int count = menuService.count(null);
//        return JsonResponseUtil.ok(count);
//    }
}