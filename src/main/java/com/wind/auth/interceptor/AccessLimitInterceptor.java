package com.wind.auth.interceptor;

import com.wind.annotation.AccessLimit;
import com.wind.auth.constants.RedisKey;
import com.wind.auth.service.RedisService;
import com.wind.common.ErrorCode;
import com.wind.passport.util.RequestUtil;
import com.wind.utils.IPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求次数拦截器
 */
@Component
public class AccessLimitInterceptor implements HandlerInterceptor {
    private final static Logger logger = LoggerFactory.getLogger(AccessLimitInterceptor.class);

    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 判断请求是否属于方法的请求
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            // 获取方法中的注解,看是否有该注解
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if (accessLimit == null) {
                return true;
            }
            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            boolean login = accessLimit.needLogin();
            String ip = IPUtil.getIP(request);
            String uri = request.getRequestURI();
            String key = RedisKey.AUTH_USER_METHOD_ACCESS_LIMIT_PREFIX + ip + "_" + uri;
            // 登录校验
            if (login) {
                // 获取登录的session进行判断
                logger.info("请求次数拦截器,未登录,ip={},uri={}", ip, uri);
                RequestUtil.failResponse(response, ErrorCode.NO_LOGIN);
                return false;
            }
            // 从redis中获取用户访问的次数
            Object obj = redisService.get(key);// 获取访问次数
            if (obj == null) {
                redisService.set(key, 1, seconds);// 第一次访问
            } else if (Integer.parseInt(obj.toString()) < maxCount) {
                redisService.incr(key, 1);// 加1
            } else {
                // 超出访问次数
                RequestUtil.failResponse(response, ErrorCode.OVER_ACCESS_TIMES);
                logger.info("请求次数拦截器,超过访问次数,ip={},uri={}", ip, uri);
                return false;
            }
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