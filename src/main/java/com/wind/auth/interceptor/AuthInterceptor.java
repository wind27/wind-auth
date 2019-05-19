package com.wind.auth.interceptor;

import com.wind.auth.model.Permission;
import com.wind.auth.model.User;
import com.wind.auth.service.RedisService;
import com.wind.auth.service.UserService;
import com.wind.common.ErrorCode;
import com.wind.passport.annotation.AuthPermission;
import com.wind.passport.util.PassportHelper;
import com.wind.passport.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 请求次数拦截器
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {
    private final static Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserService userService;

    @Autowired
    private PassportHelper passportHelper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 判断请求是否属于方法的请求
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            // 获取方法中的注解,看是否有该注解
            AuthPermission authPermission = hm.getMethodAnnotation(AuthPermission.class);
            if (authPermission == null) {
                return true;
            }

            //校验用户登录信息
            User user = passportHelper.getUserFromRequest(request);
            if(user == null) {
                RequestUtil.failResponse(response, ErrorCode.NO_LOGIN);
                return false;
            }

            //权限校验
            String permissionValue = authPermission.value();
            Long uid = user.getId();
            List<Permission> list = userService.findPermissionByUserId(uid);
            for(Permission permission : list) {
                if(permission != null && permissionValue.equals(permission.getValue())) {
                    return true;
                }
            }

            logger.info("权限拦截器,没有操作权限,uid={},permission.value={},permissionList={}", uid, permissionValue, list);
            RequestUtil.failResponse(response, ErrorCode.NO_PREMISSION);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o,
            ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e)
            throws Exception {
    }
}