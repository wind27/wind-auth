package com.wind.auth.controller;

import com.wind.auth.service.UserService;
import com.wind.common.ErrorCode;
import com.wind.utils.JsonResponseUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/shiro")
public class ShiraController {
    private final static Logger logger = LoggerFactory.getLogger(ShiraController.class);

    @Autowired
    private UserService userService;

    /**
     * 发送邮件 tName 邮件模板名称
     * 
     * @return 返回结果
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public Object list(HttpServletRequest request, @RequestParam("username") String username,
            @RequestParam("password") String password) {
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);

            Subject subject = SecurityUtils.getSubject();
            try {
                subject.login(token);
                request.getSession().setAttribute("user", subject.getPrincipal());
                return JsonResponseUtil.ok();
            } catch (Exception e) {
                return JsonResponseUtil.fail(ErrorCode.SYS_ERROR);
            }
        } catch (Exception e) {
            logger.error("shiro 登录, 异常", e);
            return JsonResponseUtil.fail(ErrorCode.SYS_ERROR);
        }
    }

    // 退出登录
    @RequestMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            subject.logout();
        }
        return "login";
    }

    // 访问login时跳到login.jsp
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    // admin角色才能访问
    @RequestMapping("/admin")
    @ResponseBody
    public String admin() {
        return "admin success";
    }

    // 有delete权限才能访问
    @RequestMapping("/edit")
    @ResponseBody
    public String edit() {
        return "edit success";
    }

    @RequestMapping("/test")
    @ResponseBody
    @RequiresRoles("guest")
    public String test() {
        return "test success";
    }
}