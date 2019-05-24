package com.wind.auth.controller;

import com.wind.auth.model.EMail;
import com.wind.auth.model.TokenVO;
import com.wind.auth.model.User;
import com.wind.auth.model.UserToken;
import com.wind.auth.service.UserService;
import com.wind.auth.service.UserTokenService;
import com.wind.common.ErrorCode;
import com.wind.utils.IPUtil;
import com.wind.utils.JsonResponseUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/jwt")
public class TokenController {
    private final static Logger logger = LoggerFactory.getLogger(TokenController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserTokenService userTokenService;

    /**
     * 发送邮件 tName 邮件模板名称
     * 
     * @return 返回结果
     */
    @ResponseBody
    @RequestMapping(value = "/token", method = RequestMethod.GET)
    public Object token(HttpServletRequest request, @RequestParam("username") String username,
            @RequestParam("password") String password) {
        try {
            Map<String, Object> data = new HashMap<>();
            if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
                return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
            }
            User user = userService.findByUsername(username);
            if (user == null) {
                return JsonResponseUtil.fail(ErrorCode.AUTH_USER_NOT_EXISTS);
            }
            // 获取userToken
            UserToken userToken = userTokenService.getLastestByUsername(username);
            data.put("token", userToken.getToken());
            return JsonResponseUtil.ok(data);
        } catch (Exception e) {
            logger.error("用户登录生成token, 异常", e);
            return JsonResponseUtil.fail(ErrorCode.SYS_ERROR);
        }
    }

}