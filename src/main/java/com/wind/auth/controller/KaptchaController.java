package com.wind.auth.controller;

import com.wind.annotation.AccessLimit;
import com.wind.auth.service.KaptchaService;
import com.wind.common.ErrorCode;
import com.wind.utils.JsonResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * KaptchaController 验证码校验
 *
 * @author qianchun 2018/12/24
 **/
@Controller
@RequestMapping("/kaptcha")
public class KaptchaController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KaptchaService kaptchaService;


    /**
     * 图片验证码生成
     * @param request request
     * @param response response
     * @return 返回结果
     */
    @ResponseBody
    @RequestMapping(value = "/image", method = RequestMethod.GET)
    @AccessLimit(seconds = 60, maxCount = 10, needLogin = false)
    public String  image(HttpServletRequest request, HttpServletResponse response) {
        kaptchaService.getImage(request, response);
        return null;
    }

    /**
     * 图片验证码校验
     * 
     * @param request 请求
     * @param kaptcha 验证码
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/verify", method = RequestMethod.GET)
    public String verify(HttpServletRequest request, @RequestParam("kaptcha") String kaptcha) {
        String sessionId = null;
        HttpSession session = request.getSession(true);
        if (session != null) {
            sessionId = session.getId();
        }
        if (StringUtils.isBlank(sessionId)) {
            logger.error("图片验证码校验, sessionId 不存在, kaptcha={}", kaptcha);
            return JsonResponseUtil.fail(ErrorCode.AUTH_USER_MISTAKE_STEP);
        }
        ErrorCode errorCode = kaptchaService.verify(sessionId, kaptcha);
        if (!ErrorCode.SUCCESS.equals(errorCode)) {
            return JsonResponseUtil.fail(errorCode);
        }
        return JsonResponseUtil.ok();
    }
}
