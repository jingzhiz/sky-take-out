package com.sky.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Slf4j
@Configuration
public class RedisConfiguration {

	@Bean
	public RedisTemplate restTemplate(RedisConnectionFactory connectionFactory) {
		log.info("开始创建 redis 模板对象...");

		RedisTemplate redisTemplate = new RedisTemplate();

		// 设置 redis 的连接工厂对象
		redisTemplate.setConnectionFactory(connectionFactory);
		// 设置 redis key 的序列化器
		redisTemplate.setKeySerializer(new StringRedisSerializer());

		return redisTemplate;
	}
}
