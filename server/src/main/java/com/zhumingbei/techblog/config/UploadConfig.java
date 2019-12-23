package com.zhumingbei.techblog.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Data
@Configuration
@ConfigurationProperties("upload")
public class UploadConfig  implements WebMvcConfigurer {
    private String root;
    private String file;
    private String avatar;
    private String image;
    private String staticAccessPath;
    private String urlPrefix;
    public String getFileRootPath() {
        return this.getRoot() + "/" + this.getFile();
    }

    public String getAvatarRootPath() {
        return this.getRoot() + "/" + this.getAvatar();
    }

    public String getImageRootPath() {
        return this.getRoot() + "/" + this.getImage();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //root必须/结尾，表明为目录
        //staticAccessPath虚拟链接到root
        registry.addResourceHandler(staticAccessPath).addResourceLocations("file:" + root);
    }
}
