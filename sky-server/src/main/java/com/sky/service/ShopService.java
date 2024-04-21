package com.sky.service;

import org.springframework.stereotype.Service;

@Service
public interface ShopService {

	/**
	 * 修改店铺状态
	 * @param status
	 */
	void updateStatus(Integer status);

	/**
	 * 查询店铺状态
	 */
	Integer getStatus();
}
