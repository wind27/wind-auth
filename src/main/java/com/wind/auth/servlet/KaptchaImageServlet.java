package com.wind.auth.servlet;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.Config;
import com.wind.auth.constants.Constant;
import com.wind.auth.service.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.imageio.ImageIO;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 * 图片验证码 servlet
 * @author  qianchun
 * @date 2019/05/09
 */
@WebServlet(urlPatterns = "/test/kaptcha/image")
public class KaptchaImageServlet extends HttpServlet implements Servlet {
    private Logger logger = LoggerFactory.getLogger(KaptchaImageServlet.class);

    @Autowired
    private RedisService redisService;

    private Properties props = new Properties();

    @Override
    public void init(ServletConfig conf) throws ServletException {
        super.init(conf);
        ImageIO.setUseCache(false);
        Enumeration<?> initParams = conf.getInitParameterNames();
        while (initParams.hasMoreElements()) {
            String key = (String) initParams.nextElement();
            String value = conf.getInitParameter(key);
            this.props.put(key, value);
        }
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, conf.getServletContext());
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String sessionId = null;
        HttpSession session = req.getSession(true);
        if (session != null) {
            sessionId = session.getId();
        }
        if (StringUtils.isBlank(sessionId)) {
            logger.error("[fyd-app-web] 图片验证码校验, sessionId 不存在");
            return ;
        }
        String widthStr = req.getParameter("width");
        String heightStr = req.getParameter("height");
        if(StringUtils.isNotBlank(widthStr)){
            this.props.put(Constant.KAPTCHA_IMAGE_WIDTH,widthStr);
        }
        if(StringUtils.isNotBlank(heightStr)){
            this.props.put(Constant.KAPTCHA_IMAGE_HEIGHT,heightStr);
        }
        Config config = new Config(this.props);
        Producer kaptchaProducer = config.getProducerImpl();
        resp.setHeader("Cache-Control", "no-store, no-cache");
        resp.setContentType("image/jpeg");

        // create the text for the image
        String kaptcha = kaptchaProducer.createText();
        logger.info("【 fyd-app 】获取图片验证码,sessionId={}, kaptcha={}", sessionId, kaptcha);
        Map<String, String> map = new HashMap<>();
        map.put(Constant.KAPTCHA_SESSION_KEY, kaptcha);
        map.put(Constant.KAPTCHA_SESSION_DATE, String.valueOf(System.currentTimeMillis()));
        redisService.setHashAll(sessionId, map);
        BufferedImage bi = kaptchaProducer.createImage(kaptcha);
        try {
            ServletOutputStream out = resp.getOutputStream();
            ImageIO.write(bi, "jpg", out);
        } catch (Exception ex) {
            logger.info("【 fyd-app 】获取图片验证码异常,sessionId={}, kaptcha={}", sessionId, kaptcha, ex);
        }
        session.setAttribute(Constant.KAPTCHA_SESSION_KEY, kaptcha);
        session.setAttribute(Constant.KAPTCHA_SESSION_DATE, new Date());
    }
}
