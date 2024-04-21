package com.sky.service.impl;

import com.sky.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class ShopServiceImpl implements ShopService {
	public final String KEY = "SHOP_STATUS";

	@Autowired
	RedisTemplate redisTemplate;

	/**
	 * 修改店铺状态
	 * @param status
	 */
	public void updateStatus(Integer status) {
		ValueOperations valueOperations = redisTemplate.opsForValue();

		valueOperations.set(KEY, status);
	}

	/**
	 * 查询店铺状态
	 */
	public Integer getStatus() {
		ValueOperations valueOperations = redisTemplate.opsForValue();

		Integer status = (Integer) valueOperations.get(KEY);

		return status;
	}
}
