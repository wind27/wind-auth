package com.wind.auth.service;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.Config;
import com.wind.auth.constants.Constant;
import com.wind.common.ErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * KaptchaService
 *
 * @author qianchun 2018/12/24
 **/
@Service
public class KaptchaService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisService redisService;

    /**
     * 生成雅正吗
     * @param request request
     * @param response response
     */
    public void getImage(HttpServletRequest request, HttpServletResponse response) {
        Properties props = new Properties();
        props.put("kaptcha.border", "no");
        props.put("kaptcha.textproducer.char.space", "4");
        props.put("kaptcha.image.width", "80");
        props.put("kaptcha.image.height", "35");
        props.put("kaptcha.textproducer.font.size", "24");
        props.put("kaptcha.textproducer.char.string", "34678abcdefgjkmpqwxy");
        props.put("kaptcha.textproducer.char.length", "4");
        props.put("kaptcha.background.clear.from", "white");
        props.put("kaptcha.background.clear.to", "white");
        props.put("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");
        // 图片样式：
        // 水纹com.google.code.kaptcha.impl.WaterRipple,
        // 鱼眼com.google.code.kaptcha.impl.FishEyeGimpy
        // 阴影com.google.code.kaptcha.impl.ShadowGimpy
        props.put("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.ShadowGimpy");
//        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, conf.getServletContext());


        String sessionId = null;
        HttpSession session = request.getSession(true);
        if (session != null) {
            sessionId = session.getId();
        }
        if (StringUtils.isBlank(sessionId)) {
            logger.error("图片验证码生成, sessionId 不存在");
            return;
        }
        String widthStr = request.getParameter("width");
        String heightStr = request.getParameter("height");
        if (StringUtils.isNotBlank(widthStr)) {
            props.put(Constant.KAPTCHA_IMAGE_WIDTH, widthStr);
        }
        if (StringUtils.isNotBlank(heightStr)) {
            props.put(Constant.KAPTCHA_IMAGE_HEIGHT, heightStr);
        }
        Config config = new Config(props);
        Producer kaptchaProducer = config.getProducerImpl();

        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache");

        // return a jpeg
        response.setContentType("image/jpeg");

        // create the text for the image
        String kaptcha = kaptchaProducer.createText();
        logger.info("图片验证码生成,sessionId={}, kaptcha={}", sessionId, kaptcha);
        Map<String, String> map = new HashMap<>();
        map.put(Constant.KAPTCHA_SESSION_KEY, kaptcha);
        map.put(Constant.KAPTCHA_SESSION_DATE, String.valueOf(System.currentTimeMillis()));
        redisService.setHashAll(sessionId, map);
        // create the image with the text
        BufferedImage bi = kaptchaProducer.createImage(kaptcha);
        try {
            ServletOutputStream out = response.getOutputStream();
            // write the data out
            ImageIO.write(bi, "jpg", out);
        } catch (Exception ex) {
            logger.info("图片验证码生成,异常,sessionId={}, kaptcha={}", sessionId, kaptcha, ex);
        }
        session.setAttribute(Constant.KAPTCHA_SESSION_KEY, kaptcha);
        session.setAttribute(Constant.KAPTCHA_SESSION_DATE, new Date());
    }

    /**
     * 图片验证码校验
     * @param sessionId sessionid
     * @param kaptcha 验证码
     * @return 返回结果
     */
    public ErrorCode verify(String sessionId, String kaptcha) {
        if (StringUtils.isBlank(kaptcha)) {
            logger.warn("fyd-app 图片验证码校验，kaptcha为空,sessionId={}, kaptcha={}",sessionId, kaptcha);
            return ErrorCode.PARAM_ERROR;
        }
        kaptcha = kaptcha.trim();
        Map<String, String> sessionIdKaptchaMap = redisService.getHashAll(sessionId);
        if (null == sessionIdKaptchaMap || sessionIdKaptchaMap.size() == 0) {
            logger.warn("fyd-app 图片验证校验,图片校验失败,sessionId={}, kaptcha={}, sessionIdKaptchaMap={}", sessionId, kaptcha, sessionIdKaptchaMap);
            return ErrorCode.IMAGE_AUTH_FAIL;
        }
        //验证码  正确性/时效性
        if ((System.currentTimeMillis() - Long.valueOf(sessionIdKaptchaMap.get(Constant.KAPTCHA_SESSION_DATE))) > Constant.IMAGE_VALID_TIME) {
            logger.warn("fyd-app 图片验证校验,图片校验超时,sessionId={}, kaptcha={}, sessionIdKaptchaMap={}", sessionId, kaptcha, sessionIdKaptchaMap);
            return ErrorCode.IMAGE_TIMEOUT;
        }
        if (!kaptcha.equalsIgnoreCase(sessionIdKaptchaMap.get(Constant.KAPTCHA_SESSION_KEY))) {
            logger.warn("fyd-app 图片验证校验,图片校验失败,sessionId={}, kaptcha={}, sessionIdKaptchaMap={}", sessionId, kaptcha,
                    sessionIdKaptchaMap);
            return ErrorCode.IMAGE_AUTH_FAIL;
        }
        sessionIdKaptchaMap.clear();
        redisService.setHashAll(sessionId, sessionIdKaptchaMap);
        return ErrorCode.SUCCESS;
    }
}
