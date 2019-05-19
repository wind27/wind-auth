package com.wind.auth.config;

import com.wind.auth.model.User;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * SessionInfoArgumentResolver
 *
 * @author qianchun
 * @date 2019/4/24
 **/
@Component
public class SessionInfoArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String attrName = "sessionInfo";
        Object sessionInfo = webRequest.getAttribute(attrName, RequestAttributes.SCOPE_SESSION);
        if (sessionInfo == null) {
            //实例化
            sessionInfo = new User();
            HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
            HttpSession session = request.getSession();
            session.setAttribute(attrName, sessionInfo);
        }
        return sessionInfo;
    }
}
