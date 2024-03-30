package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DishService {
	/**
	 * 分页查询菜品
	 * @param dishPageQueryDTO
	 * @return
	 */
	PageResult query(DishPageQueryDTO dishPageQueryDTO);

	/**
	 * 新增菜品
	 * @param dishDTO
	 * @return
	 */
	void create(DishDTO dishDTO);

	/**
	 * 删除菜品
	 * @param ids
	 * @return
	 */
	void delete(List<Long> ids);
}
