package com.xwhking.yuapi;

import com.xwhking.interfacestarter.client.XWHKINGClient;
import com.xwhking.yuapi.config.WxOpenConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * 主类测试
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@SpringBootTest
class MainApplicationTests {

    @Resource
    private WxOpenConfig wxOpenConfig;
    @Resource
    private XWHKINGClient xwhkingClient;
    @Resource
    RedisTemplate<String , Object> redisTemplate;

    @Test
    void testRedis(){
        redisTemplate.opsForValue().set("Hello","NiMa");
        System.out.println(redisTemplate.opsForValue().get("Hello"));
    }
}
