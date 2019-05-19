package com.wind.auth.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 创建 FastJsonConfiguration
 *
 * @author qianchun
 * @date 2019/4/25
 **/
//@Configuration
public class FastJsonConfiguration {

    @Bean
    public FastJsonHttpMessageConverter getFastJsonConfiguration() {
        // 创建fastJson消息转换器
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        // 创建配置类
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        // 修改配置返回内容的过滤
        fastJsonConfig.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        return fastConverter;
    }
}
