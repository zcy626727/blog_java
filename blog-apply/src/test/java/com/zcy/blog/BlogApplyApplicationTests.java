package com.zcy.blog;

import com.zcy.blog.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;


@SpringBootTest
class BlogApplyApplicationTests {
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ArticleService articleService;
    @Test
    void contextLoads() {
//        BoundHashOperations h = redisTemplate.boundHashOps("test1");
//        System.out.println(h.get("123"));
//        h.increment("123",1);
//        h.increment("123",1);
//        BoundHashOperations h2 = redisTemplate.boundHashOps("test1");
//        System.out.println(h2.get("123").toString());

//        articleService.incrementWatchCountById("1");
        System.out.println(articleService.getWatchCountById("1"));

    }

}
