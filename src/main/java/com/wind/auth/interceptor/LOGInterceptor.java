package com.wind.auth.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wind.auth.model.LoggerEntity;
import com.wind.auth.wrapper.ResponseWrapper;
import com.wind.utils.HeaderUtil;
import com.wind.utils.IPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 请求次数拦截器
 */
@Component
public class LOGInterceptor implements HandlerInterceptor {
    private final static Logger logger = LoggerFactory.getLogger(LOGInterceptor.class);

    private static final String LOGGER_ENTITY = "_reqeust_log_entity";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 创建日志实体
        LoggerEntity loggerEntity = new LoggerEntity();

        // 获取请求sessionId
        String sessionId = request.getRequestedSessionId();

        // 设置请求地址
        String url = request.getRequestURI();
        loggerEntity.setUri(url);

        // 获取请求参数信息
        String paramData = JSON.toJSONString(request.getParameterMap(),
                SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue);
        loggerEntity.setParamData(paramData);

        // 设置客户端ip
        loggerEntity.setClientIp(IPUtil.getIP(request));

        // 设置请求方法
        loggerEntity.setMethod(request.getMethod());

        // 设置请求类型（json|普通请求）
        loggerEntity.setType(HeaderUtil.getRequestType(request));

        // 设置sessionId
        loggerEntity.setSessionId(sessionId);

        // 设置请求开始时间
        loggerEntity.setRequestTime(new Date());

        // 设置请求实体到request内，方面afterCompletion方法调用
        request.setAttribute(LOGGER_ENTITY, loggerEntity);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o,
            ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e)
            throws Exception {
        Date now = new Date();
        // 获取请求错误码
        int status = response.getStatus();
        // 获取本次请求日志实体
        LoggerEntity loggerEntity = (LoggerEntity) request.getAttribute(LOGGER_ENTITY);
        // 设置请求时间差
        loggerEntity.setTimeConsuming(
                Integer.valueOf(String.valueOf(now.getTime() - loggerEntity.getRequestTime().getTime())));
        // 设置返回时间
        loggerEntity.setReturnTime(now);
        // 设置返回错误码
        loggerEntity.setHttpStatusCode(status);
        // 设置返回值
        String content = ((ResponseWrapper) response).getContent();
        // 设置返回值
        loggerEntity.setReturnData(content);
        // 执行将日志写入数据库
        LoggerDAO loggerDAO = getDAO(LoggerDAO.class, request);
        loggerDAO.save(loggerEntity);
    }

    /**
     * 根据传入的类型获取spring管理的对应dao
     * 
     * @param clazz 类型
     * @param request 请求对象
     * @param <T> T
     * @return 返回结果
     */
    private <T> T getDAO(Class<T> clazz, HttpServletRequest request) {
        BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        return factory.getBean(clazz);
    }

    private class LoggerDAO {
        @Bean
        LoggerDAO getLoggerDAO() {
            return new LoggerDAO();
        }

        void save(LoggerEntity loggerEntity) {
            logger.info("请求信息:logEntity={}", loggerEntity);
        }
    }
}
