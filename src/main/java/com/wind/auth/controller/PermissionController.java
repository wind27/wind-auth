package com.wind.auth.controller;

import com.wind.auth.model.Permission;
import com.wind.auth.model.PermissionVO;
import com.wind.auth.model.User;
import com.wind.auth.service.PermissionService;
import com.wind.auth.service.UserService;
import com.wind.common.ErrorCode;
import com.wind.common.Page;
import com.wind.passport.annotation.AuthPermission;
import com.wind.utils.JsonResponseUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/permission")
public class PermissionController {
    private final static Logger logger = LoggerFactory.getLogger(PermissionController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    /**
     * 权限列表
     * 
     * @return 返回结果
     */
    @ResponseBody
    @AuthPermission("auth.permission.list")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object list(@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) {
        try {
            Map<String, Object> data = new HashMap<>();
            Page pageModel = new Page(pageNo, pageSize);
            Map<String, Object> params = new HashMap<>();
            params.put("page", pageModel);

            List<Permission> list = permissionService.findPage(params);
            int total = permissionService.count(params);
            pageModel.setTotal(total);
            data.put("list", list);
            data.put("page", pageModel);
            return JsonResponseUtil.ok(data);
        } catch (Exception e) {
            logger.error("用户列表, 异常", e);
            return JsonResponseUtil.fail(ErrorCode.SYS_ERROR);
        }
    }

    /**
     * 权限详情
     *
     * @return 返回结果
     */
    @ResponseBody
    // @AuthPermission("auth.permission.detail")
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public Object detail(@PathVariable("id") Long id) {
        Map<String, Object> data = new HashMap<>();
        if (id == null) {
            return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
        }
        Permission permission = permissionService.getById(id);
        if (permission == null) {
            return JsonResponseUtil.fail(ErrorCode.AUTH_PERMISSION_NOT_EXISTS);
        }
        data.put("permission", permission);
        return JsonResponseUtil.ok(data);
    }

    /**
     * 权限新增/更新
     *
     * @return 返回结果
     */
    @ResponseBody
    // @AuthPermission("auth.permission.update")
    @RequestMapping(value = "/save", method = RequestMethod.GET)
    public Object add(Permission param) {
        if (StringUtils.isEmpty(param.getValue()) || StringUtils.isEmpty(param.getName())) {
            return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
        }
        Permission permission = permissionService.getByValue(param.getValue());
        if (param.getId() == null && permission != null) {
            return JsonResponseUtil.fail(ErrorCode.AUTH_PERMISSION_VALUE_EXISTS);
        } else if (param.getId() != null && permission != null && !param.getId().equals(permission.getId())) {

        }
        if (permission == null) {
            permission = new Permission();
        }
        permission.setName(param.getName());
        permission.setValue(param.getValue());
        permission.setStatus(1);
        permission.setCreateTime(new Date());
        permission.setUpdateTime(new Date());
        boolean flag = permissionService.saveOrUpdate(permission);
        if (flag) {
            return JsonResponseUtil.ok();
        } else {
            return JsonResponseUtil.fail(ErrorCode.FAIL);
        }
    }

    /**
     * 权限删除
     *
     * @return 返回结果
     */
    @ResponseBody
    // @AuthPermission("auth.permission.delete")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public Object delete(@PathVariable("id") Long id) {
        if (id == null) {
            return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
        }
        boolean flag = permissionService.delete(id);
        if (flag) {
            return JsonResponseUtil.ok();
        } else {
            return JsonResponseUtil.fail();
        }
    }

    /**
     * 权限启用
     *
     * @return 返回结果
     */
    @ResponseBody
    // @AuthPermission("auth.permission.enable")
    @RequestMapping(value = "/enable/{id}", method = RequestMethod.GET)
    public Object enable(@PathVariable("id") Long id) {
        if (id == null) {
            return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
        }
        Permission permission = permissionService.getById(id);
        if (permission == null) {
            return JsonResponseUtil.fail(ErrorCode.AUTH_PERMISSION_NOT_EXISTS);
        }
        permission.setStatus(1);
        permission.setUpdateTime(new Date());
        permissionService.update(permission);
        return JsonResponseUtil.ok();
    }

    /**
     * 权限停用
     *
     * @return 返回结果
     */
    @ResponseBody
    // @AuthPermission("auth.permission.disable")
    @RequestMapping(value = "/disable/{id}", method = RequestMethod.GET)
    public Object disable(@PathVariable("id") Long id) {
        if (id == null) {
            return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
        }
        Permission permission = permissionService.getById(id);
        if (permission == null) {
            return JsonResponseUtil.fail(ErrorCode.AUTH_PERMISSION_NOT_EXISTS);
        }
        permission.setStatus(0);
        permission.setUpdateTime(new Date());
        permissionService.update(permission);
        return JsonResponseUtil.ok();
    }

    @ResponseBody
    @RequestMapping(value = "/permission/{userId}", method = RequestMethod.GET)
    public Object permissionList(@PathVariable("userId") Long userId) {
        List<Permission> hasPermissionList = userService.findPermissionByUserId(1L);
        List<Permission> allPermission = permissionService.findPage(null);
        PermissionVO root = permissionService.sort(allPermission, hasPermissionList);
        if (root == null) {
            return JsonResponseUtil.fail(ErrorCode.AUTH_PERMISSION_NOT_EXISTS);
        }
        return JsonResponseUtil.ok(root);
    }
}