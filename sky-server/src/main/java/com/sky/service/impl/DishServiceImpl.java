package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {
	@Autowired
	DishMapper dishMapper;
	@Autowired
	DishFlavorMapper dishFlavorMapper;

	/**
	 * 分页查询菜品
	 * @param dishPageQueryDTO
	 * @return
	 */
	public PageResult query(DishPageQueryDTO dishPageQueryDTO) {
		PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());

		Page<DishVO> page = dishMapper.query(dishPageQueryDTO);

		Long total = page.getTotal();
		List<DishVO> records = page.getResult();

		return new PageResult(total, records);
	}

	/**
	 * 新增菜品
	 * @param dishDTO
	 * @return
	 */
	public void create(DishDTO dishDTO) {
		// 插入菜品
		Dish dish = new Dish();
		BeanUtils.copyProperties(dishDTO, dish);
		dishMapper.create(dish);

		// 获取 sql 插入后的主键值
		Long id = dish.getId();

		// 插入口味
		List<DishFlavor> flavors = dishDTO.getFlavors();
		if (flavors != null && flavors.size() > 0) {
			for (DishFlavor flavor : flavors) {
				flavor.setDishId(id);
			}
			dishFlavorMapper.insertBatch(flavors);
		}
	}

	/**
	 * 删除菜品
	 * @param ids
	 * @return
	 */
	public void delete(List<Long> ids) {

	}
}
