package com.wind.auth.config;

import com.wind.auth.interceptor.AccessLimitInterceptor;
import com.wind.auth.interceptor.AuthInterceptor;
import com.wind.passport.spring.argument.resolver.PwdHandlerMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

/**
 * WebConfigurer
 *
 * @author qianchun
 * @date 2019/4/16
 **/
@Configuration
public class WebConfigurer {

    @Autowired
    private AccessLimitInterceptor accessLimitInterceptor;

    @Autowired
    private AuthInterceptor authInterceptor;

    @Autowired
    private PwdHandlerMethodArgumentResolver pwdHandlerMethodArgumentResolver;

    @Autowired
    private SessionInfoArgumentResolver sessionInfoArgumentResolver;

    // @Autowired
    // private LoginInterceptor loginInterceptor;

    // @Autowired
    // private FastJsonHttpMessageConverter fastJsonHttpMessageConverter;

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurerAdapter() {

            /**
             * 添加拦截器
             *
             * @param registry registry
             */
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
//                registry.addInterceptor(accessLimitInterceptor).addPathPatterns("/**");
//                registry.addInterceptor(authInterceptor).addPathPatterns("/**");

                // registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/kaptcha/");
            }

            /**
             * 新增跨域访问
             * 
             * @param corsRegistry corsRegistry
             */
            @Override
            public void addCorsMappings(CorsRegistry corsRegistry) {
                // corsRegistry.addMapping("/**");
                corsRegistry.addMapping("/**").allowedOrigins("http://admin.wind.com:4200")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD").allowCredentials(false).maxAge(3600);

                // corsRegistry.addMapping("/**")//设置允许跨域的路径
                // .allowedOrigins("*")//设置允许跨域请求的域名
                // .allowCredentials(true)//是否允许证书 不再默认开启
                // .allowedMethods("GET", "POST", "PUT", "DELETE")//设置允许的方法
                // .maxAge(3600);//跨域允许时间
            }

            @Override
            public void configurePathMatch(PathMatchConfigurer pathMatchConfigurer) {

            }

            /**
             * 配置内容裁决的一些选项
             * 
             * @param contentNegotiationConfigurer contentNegotiationConfigurer
             */
            @Override
            public void configureContentNegotiation(ContentNegotiationConfigurer contentNegotiationConfigurer) {

            }

            @Override
            public void configureAsyncSupport(AsyncSupportConfigurer asyncSupportConfigurer) {

            }

            /**
             * 默认静态资源处理器
             * 
             * @param defaultServletHandlerConfigurer defaultServletHandlerConfigurer
             */
            @Override
            public void configureDefaultServletHandling(
                    DefaultServletHandlerConfigurer defaultServletHandlerConfigurer) {

            }

            @Override
            public void addFormatters(FormatterRegistry formatterRegistry) {

            }

            /**
             * 静态资源处理
             * 
             * @param registry resourceHandlerRegistry
             */
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/xxx/xxxx/resources/**").addResourceLocations("classpath:/static/");
            }

            /**
             * 视图跳转控制器
             * 
             * @param viewControllerRegistry viewControllerRegistry
             */
            @Override
            public void addViewControllers(ViewControllerRegistry viewControllerRegistry) {

            }

            /**
             * 这里配置视图解析器
             * 
             * @param viewResolverRegistry viewResolverRegistry
             */
            @Override
            public void configureViewResolvers(ViewResolverRegistry viewResolverRegistry) {

            }

            /**
             * 参数解析器
             *
             * @param argumentResolvers argumentResolvers
             */
            @Override
            public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
                argumentResolvers.add(pwdHandlerMethodArgumentResolver);
                argumentResolvers.add(sessionInfoArgumentResolver);

                super.addArgumentResolvers(argumentResolvers);
            }

            @Override
            public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> list) {

            }

            /**
             * 返回结果转化 json
             * 
             * @param httpMessageConverters httpMessageConverters
             */
            @Override
            public void configureMessageConverters(List<HttpMessageConverter<?>> httpMessageConverters) {
                // super.configureMessageConverters(httpMessageConverters);
                // httpMessageConverters.add(fastJsonHttpMessageConverter);
            }

            @Override
            public void extendMessageConverters(List<HttpMessageConverter<?>> list) {

            }

            @Override
            public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> list) {

            }

            @Override
            public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> list) {

            }

            @Override
            public Validator getValidator() {
                return null;
            }

            @Override
            public MessageCodesResolver getMessageCodesResolver() {
                return null;
            }
        };
    }
}
