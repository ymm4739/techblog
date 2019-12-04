package com.zhumingbei.techblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableRedisHttpSession
@MapperScan("com.zhumingbei.techblog.mapper")
public class TechblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechblogApplication.class, args);
    }

}
