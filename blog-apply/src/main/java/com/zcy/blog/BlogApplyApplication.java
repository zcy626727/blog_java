package com.zcy.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
@MapperScan("com.zcy.blog.mapper")
public class BlogApplyApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplyApplication.class, args);
    }


}
