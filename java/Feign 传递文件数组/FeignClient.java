package com.bonc.business.acedoctor.feign;

import com.bonc.business.acedoctor.config.MultipartSupportConfig;
import feign.form.spring.SpringFormEncoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import feign.codec.Encoder;


import java.util.List;
import java.util.Map;


/**
 * @author Bruce Liu
 * @version V1.0.0
 * @ClassName AdminServiceFeign
 * @Description
 * @date 2019-09-06 9:54
 */

@FeignClient(value = "ace-fastdfs", configuration = MultipartSupportConfig.class)
public interface FastDfsFeignClient {
    /**
     * 图片列表
     *
     * @param pics             需要传入的图片
     * @param createThumbImage 是否需要生成缩略图
     * @return
     */
    @PostMapping(value = "/upload/pics", consumes = "multipart/form-data")
    List<Map<String, Object>> uploadPics(@RequestPart("pics") MultipartFile[] pics,
                                         @RequestParam("createThumbImage") boolean createThumbImage);


    /**
     * 上传其他类型的文件
     *
     * @param files 其他类型的文件
     * @return
     */
    @PostMapping(value = "/upload/othertype", consumes = "multipart/form-data")
    List<Map<String, Object>> uploadOtherFiles(@RequestPart("recordFile") MultipartFile[] files);


}