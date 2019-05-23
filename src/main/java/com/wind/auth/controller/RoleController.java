package com.wind.auth.controller;

import com.wind.auth.model.Permission;
import com.wind.auth.model.Role;
import com.wind.auth.model.RoleVO;
import com.wind.auth.model.User;
import com.wind.auth.service.PermissionService;
import com.wind.auth.service.RoleService;
import com.wind.common.ErrorCode;
import com.wind.common.Page;
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

/**
 * 角色管理
 * 
 * @author qianchun
 */
@Controller
@RequestMapping("/role")
public class RoleController {
    private final static Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    /**
     * 角色列表
     * 
     * @return 返回结果
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object list(@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) {
        try {
            Map<String, Object> data = new HashMap<>();
            Page pageModel = new Page(pageNo, pageSize);
            Map<String, Object> params = new HashMap<>();
            params.put("page", pageModel);

            List<Role> list = roleService.findPage(params);
            int total = roleService.count(params);
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
     * 角色详情
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
        Role role = roleService.findById(id);
        if (role == null) {
            return JsonResponseUtil.fail(ErrorCode.ROLE_NOT_EXISTS);
        }
        data.put("role", role);
        return JsonResponseUtil.ok(data);
    }

    /**
     * 角色新增
     *
     * @return 返回结果
     */
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.GET)
    public Object add(Role param) {
        if (StringUtils.isEmpty(param.getName())) {
            return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
        }
        boolean canUse = roleService.canUseRoleName(param.getName());
        if (!canUse) {
            return JsonResponseUtil.fail(ErrorCode.ROLE_HAS_EXISTS);
        }
        Role role = new Role();
        role.setName(param.getName());
        role.setStatus(1);
        role.setId(param.getId());
        role.setCreateTime(new Date());
        role.setUpdateTime(new Date());
        boolean flag = roleService.saveOrUpdate(role);
        if (flag) {
            return JsonResponseUtil.ok();
        } else {
            return JsonResponseUtil.fail(ErrorCode.FAIL);
        }
    }

    /**
     * 更新该角色权限
     * 
     * @param roleId 角色id
     * @param permissionIds 权限ids
     * @return 返回角色
     */
    @ResponseBody
    @RequestMapping(value = "permission/update", method = RequestMethod.GET)
    public Object permissionUpdate(@RequestParam("roleId") Long roleId,
            @RequestParam("permissionIds") String permissionIds) {
        if (roleId == null || StringUtils.isEmpty(permissionIds)) {
            return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
        }
        List<Long> permissionIdList = new ArrayList<>();
        Arrays.asList(permissionIds.split(",")).forEach(idStr -> {
            idStr = idStr.trim();
            if (StringUtil.isNumeric(idStr) && !permissionIdList.contains(Long.parseLong(idStr))) {
                permissionIdList.add(Long.parseLong(idStr));
            }
        });
        if (CollectionUtils.isEmpty(permissionIdList)) {
            logger.error("角色权限更新, permissionIds 为空");
            return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
        }
        ErrorCode errorCode = roleService.updatePermission(roleId, permissionIdList);
        if (ErrorCode.SUCCESS.equals(errorCode)) {
            return JsonResponseUtil.ok();
        } else {
            return JsonResponseUtil.fail(errorCode);
        }
    }

    /**
     * 角色权限列表
     * 
     * @param id 角色id
     * @return 返回结果
     */
    @ResponseBody
    @RequestMapping(value = "permission/{id}", method = RequestMethod.GET)
    public Object permissionUpdate(@PathVariable("id") Long id) {
        Map<String, Object> data = new HashMap<>();
        if (id == null) {
            return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
        }
        List<Permission> list = roleService.getPermissionByRoleId(id);
        data.put("list", list);
        return JsonResponseUtil.ok(data);
    }

    /**
     * 角色删除
     *
     * @return 返回结果
     */
    @ResponseBody
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public Object delete(@PathVariable("id") Long id) {
        if (id == null) {
            return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
        }
        boolean flag = roleService.delete(id);
        if (flag) {
            return JsonResponseUtil.ok();
        } else {
            return JsonResponseUtil.fail();
        }
    }

    /**
     * 角色启用
     *
     * @return 返回结果
     */
    @ResponseBody
    @RequestMapping(value = "/enable/{id}", method = RequestMethod.GET)
    public Object enable(@PathVariable("id") Long id) {
        if (id == null) {
            return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
        }
        Role role = roleService.findById(id);
        if (role == null) {
            return JsonResponseUtil.fail(ErrorCode.USER_NOT_EXISTS);
        }
        role.setStatus(1);
        role.setUpdateTime(new Date());
        roleService.saveOrUpdate(role);
        return JsonResponseUtil.ok();
    }

    /**
     * 角色停用
     *
     * @return 返回结果
     */
    @ResponseBody
    @RequestMapping(value = "/disable/{id}", method = RequestMethod.GET)
    public Object disable(@PathVariable("id") Long id) {
        if (id == null) {
            return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
        }
        Role role = roleService.findById(id);
        if (role == null) {
            return JsonResponseUtil.fail(ErrorCode.ROLE_NOT_EXISTS);
        }
        role.setStatus(0);
        role.setUpdateTime(new Date());
        roleService.saveOrUpdate(role);
        return JsonResponseUtil.ok(role);
    }

    // /**
    // * 角色统计
    // *
    // * @return 返回结果
    // */
    // @ResponseBody
    // @RequestMapping(value = "/count", method = RequestMethod.GET)
    // public Object count(User user) {
    //
    // Map<String, Object> param = new HashMap<>();
    //
    // int count = roleService.count(param);
    // return JsonResponseUtil.ok(count);
    // }
    //
    //
    //
    // @ResponseBody
    // @RequestMapping(value = "/test", method = RequestMethod.GET)
    // public Object list() {
    // try {
    //
    // List<RoleVO> list = roleService.findVOById(1L);
    // return JsonResponseUtil.ok(list);
    // } catch (Exception e) {
    // logger.error("角色列表, 异常", e);
    // return JsonResponseUtil.fail(ErrorCode.SYS_ERROR);
    // }
    // }
}