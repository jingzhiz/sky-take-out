package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {
	@Autowired
	DishMapper dishMapper;
	/**
	 * 分页查询菜品
	 * @param dishPageQueryDTO
	 * @return
	 */
	public PageResult query(DishPageQueryDTO dishPageQueryDTO) {
		PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());

		Page<Dish> page = dishMapper.query(dishPageQueryDTO);

		Long total = page.getTotal();
		List<Dish> records = page.getResult();

		return new PageResult(total, records);
	}
}
