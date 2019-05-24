package com.wind.auth.controller;

import com.wind.auth.model.Permission;
import com.wind.auth.model.RoleVO;
import com.wind.auth.model.User;
import com.wind.auth.model.UserVO;
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
@RequestMapping("/user")
public class UserController {
    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 用户列表
     * 
     * @return 返回结果
     */
    @ResponseBody
    @AuthPermission("auth.user.list")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object list(@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) {
        try {
            Map<String, Object> data = new HashMap<>();
            Page pageModel = new Page(pageNo, pageSize);
            Map<String, Object> params = new HashMap<>();
            params.put("page", pageModel);

            List<User> list = userService.findPage(params);
            int total = userService.count(params);

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
     * 用户详情
     *
     * @return 返回结果
     */
    @ResponseBody
//    @AuthPermission("auth.user.detail")
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public Object detail(@PathVariable("id") Long id) {
        Map<String, Object> data = new HashMap<>();
        if (id == null) {
            return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
        }
        User user = userService.findById(id);
        if (user == null) {
            return JsonResponseUtil.fail(ErrorCode.AUTH_USER_NOT_EXISTS);
        }
        data.put("user", user);
        return JsonResponseUtil.ok(data);
    }

    /**
     * 用户新增
     *
     * @return 返回结果
     */
    @ResponseBody
//    @AuthPermission("auth.user.add")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public Object add(User param) {
        if (StringUtils.isEmpty(param.getMobile())) {
            return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
        }
        User user = userService.findByMobile(param.getMobile());
        if (user != null) {
            return JsonResponseUtil.fail(ErrorCode.AUTH_USER_MOBILE_HAS_REGIST);
        }
        user = new User();
        user.setMobile(param.getMobile());
        user.setStatus(1);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        boolean flag = userService.saveOrUpdate(user);
        if (flag) {
            return JsonResponseUtil.ok();
        } else {
            return JsonResponseUtil.fail(ErrorCode.FAIL);
        }
    }

    /**
     * 用户删除
     *
     * @return 返回结果
     */
    @ResponseBody
//    @AuthPermission("auth.user.delete")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public Object delete(@PathVariable("id") Long id) {
        if (id == null) {
            return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
        }
        boolean flag = userService.delete(id);
        if (flag) {
            return JsonResponseUtil.ok();
        } else {
            return JsonResponseUtil.fail();
        }
    }

    /**
     * 用户启用
     *
     * @return 返回结果
     */
    @ResponseBody
//    @AuthPermission("auth.user.enable")
    @RequestMapping(value = "/enable/{id}", method = RequestMethod.GET)
    public Object enable(@PathVariable("id") Long id) {
        if (id == null) {
            return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
        }
        User user = userService.findById(id);
        if (user == null) {
            return JsonResponseUtil.fail(ErrorCode.AUTH_USER_NOT_EXISTS);
        }
        user.setStatus(1);
        user.setUpdateTime(new Date());
        userService.saveOrUpdate(user);
        return JsonResponseUtil.ok();
    }

    /**
     * 用户停用
     *
     * @return 返回结果
     */
    @ResponseBody
//    @AuthPermission("auth.user.disable")
    @RequestMapping(value = "/disable/{id}", method = RequestMethod.GET)
    public Object disable(@PathVariable("id") Long id) {
        if (id == null) {
            return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
        }
        User user = userService.findById(id);
        if (user == null) {
            return JsonResponseUtil.fail(ErrorCode.AUTH_USER_NOT_EXISTS);
        }
        user.setStatus(0);
        user.setUpdateTime(new Date());
        userService.saveOrUpdate(user);
        return JsonResponseUtil.ok();
    }

    /**
     * 更新用户绑定角色
     *
     * @param id 用户id
     * @param roleIds 用户角色ids
     * @return 返回结果
     */
    @ResponseBody
//    @AuthPermission("auth.user.role.update")
    @RequestMapping(value = "/role/update/", method = RequestMethod.GET)
    public Object permissionList(@RequestParam("id") Long id, @RequestParam("roleIds") String roleIds) {
        if (roleIds == null || StringUtils.isEmpty(roleIds)) {
            return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
        }
        List<Long> roleIdList = new ArrayList<>();
        Arrays.asList(roleIds.split(",")).forEach(idStr -> {
            idStr = idStr.trim();
            if (StringUtil.isNumeric(idStr) && !roleIdList.contains(Long.parseLong(idStr))) {
                roleIdList.add(Long.parseLong(idStr));
            }
        });
        if (CollectionUtils.isEmpty(roleIdList)) {
            logger.error("用户绑定角色更新, roleIds 为空");
            return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
        }
        ErrorCode errorCode = userService.updateRolesById(id, roleIdList);
        if (ErrorCode.SUCCESS.equals(errorCode)) {
            return JsonResponseUtil.ok();
        } else {
            return JsonResponseUtil.fail(errorCode);
        }
    }

    /**
     * 获取用户权限列表
     * 
     * @param id 用户id
     * @return 返回结果
     */
    @ResponseBody
//    @AuthPermission("auth.user.permission.list")
    @RequestMapping(value = "/permission/{id}", method = RequestMethod.GET)
    public Object permissionList(@PathVariable("id") Long id) {
        Map<String, Object> data = new HashMap<>();
        if (id == null) {
            return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
        }
        List<Permission> list = userService.findPermissionByUserId(id);
        data.put("list", list);
        return JsonResponseUtil.ok(data);
    }
}