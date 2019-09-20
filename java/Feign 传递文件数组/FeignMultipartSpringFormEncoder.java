package com.bonc.business.acedoctor.config;

import feign.form.FormEncoder;

import static feign.form.ContentType.MULTIPART;
import static java.util.Collections.singletonMap;

import java.lang.reflect.Type;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import feign.form.FormEncoder;
import feign.form.MultipartFormContentProcessor;

import feign.form.spring.SpringManyMultipartFilesWriter;
import feign.form.spring.SpringSingleMultipartFileWriter;
import lombok.val;
import org.springframework.web.multipart.MultipartFile;

/**
 * 重写了 SpringFormEncoder 加入对数组的支持
 *
 * @author Bruce Liu
 * @version V1.0.0
 * @ClassName fsdf
 * @Description
 * @date 2019-09-16 16:59
 */
public class FeignMultipartSpringFormEncoder extends FormEncoder {
    public FeignMultipartSpringFormEncoder() {
        this(new Encoder.Default());
    }

    /**
     * Constructor with specified delegate encoder.
     *
     * @param delegate delegate encoder, if this encoder couldn't encode object.
     */
    public FeignMultipartSpringFormEncoder(Encoder delegate) {
        super(delegate);

        val processor = (MultipartFormContentProcessor) getContentProcessor(MULTIPART);
        processor.addFirstWriter(new SpringSingleMultipartFileWriter());
        processor.addFirstWriter(new SpringManyMultipartFilesWriter());
    }

    @Override
    public void encode(Object object, Type bodyType, RequestTemplate template) throws EncodeException {
        if (bodyType.equals(MultipartFile.class)) {
            // 单MultipartFile判断
            val file = (MultipartFile) object;
            val data = singletonMap(file.getName(), object);
            super.encode(data, MAP_STRING_WILDCARD, template);
            return;

        } else if (bodyType.equals(MultipartFile[].class)) {
            // MultipartFile数组处理
            val file = (MultipartFile[]) object;
            if (file != null) {
                val data = singletonMap(file.length == 0 ? "" : file[0].getName(), object);
                super.encode(data, MAP_STRING_WILDCARD, template);
                return;
            }
        }
        // 其他类型调用父类默认处理方法
        super.encode(object, bodyType, template);
    }
}