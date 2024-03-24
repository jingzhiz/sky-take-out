package com.sky.service;

import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import org.springframework.stereotype.Service;

@Service
public interface DishService {
	/**
	 * 分页查询菜品
	 * @param dishPageQueryDTO
	 * @return
	 */
	PageResult query(DishPageQueryDTO dishPageQueryDTO);
}
