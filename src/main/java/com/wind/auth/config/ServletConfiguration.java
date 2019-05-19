package com.wind.auth.config;

import com.wind.auth.servlet.KaptchaImageServlet;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ServletConfiguration
 *
 * @author qianchun
 * @date 2019/5/9
 **/
//@Configuration
//@ServletComponentScan
public class ServletConfiguration {
    /*@Bean
    public ServletRegistrationBean servletRegistrationBean() {
        return new ServletRegistrationBean(new KaptchaImageServlet(), "/test/kaptcha/image");
    }*/
}
