package com.bonc.business.acedoctor.config;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Bruce Liu
 * @version V1.0.0
 * @ClassName MultipartSupportConfig
 * @Description
 * @date 2019-09-16 16:17
 */
@Configuration
public class MultipartSupportConfig {


    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;

    @Bean
    public Encoder multipartFormEncoder() {
        // 为Encoder注入messageConverters
        return new FeignMultipartSpringFormEncoder(new SpringEncoder(messageConverters));
    }

}
