package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {
	@Autowired
	DishMapper dishMapper;
	@Autowired
	DishFlavorMapper dishFlavorMapper;
	@Autowired
	SetmealDishMapper setmealDishMapper;

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
		dish.setStatus(StatusConstant.DISABLE);
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
		// 判断当前菜品是否能够删除 - 是否是启售中的菜品
		for (Long id : ids) {
			Dish dish = dishMapper.getById(id);
			if (dish.getStatus() == StatusConstant.ENABLE) {
				// 当前菜品处于启售中，不能删除
				throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
			}
		}
		// 判断当前菜品是否能够删除 - 是否被套餐关联
		List<Long> setmealIds = setmealDishMapper.getSetmealIdsByDishIds(ids);
		if (setmealIds != null && setmealIds.size() > 0) {
			// 当前菜品被套餐关联，不能删除
			throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
		}

		// 删除菜品表中数据
		dishMapper.delete(ids);
		// 删除口味数据
		dishFlavorMapper.deleteByDishIds(ids);
	}

	/**
	 * 根据 id 查询菜品
	 * @param id
	 * @return
	 */

	public DishVO getById(Long id) {
		DishVO dishVO = new DishVO();
		Dish dish = dishMapper.getById(id);
		List<DishFlavor> dishFlavor = dishFlavorMapper.getByDishId(id);

		BeanUtils.copyProperties(dish, dishVO);
		dishVO.setFlavors(dishFlavor);

		return dishVO;
	}

	/**
	 * 根据 categoryId 查询菜品列表
	 * @param categoryId
	 */
	public List<DishVO> list(Long categoryId) {
		return dishMapper.list(categoryId);
	}

	/**
	 * 修改菜品数据
	 * @param dishDTO
	 * @return
	 */
	public void update(DishDTO dishDTO) {
		Dish dish = new Dish();
		BeanUtils.copyProperties(dishDTO, dish);

		dishMapper.update(dish);

		Long dishId = dishDTO.getId();
		List<Long> dishIds = new ArrayList();
		dishIds.add(dishId);
		List<DishFlavor> dishFlavor = dishDTO.getFlavors();
		dishFlavorMapper.deleteByDishIds(dishIds);
		if (dishFlavor != null && dishFlavor.size() > 0) {
			for (DishFlavor flavor : dishFlavor) {
				flavor.setDishId(dishId);
			}
			dishFlavorMapper.insertBatch(dishFlavor);
		}
	}

	/**
	 * 修改菜品启停售状态
	 * @param status
	 * @param id
	 * @return
	 */
	public void updateStatus(Integer status, Long id) {
		Dish dish = new Dish();
		dish.setId(id);
		dish.setStatus(status);
		dishMapper.update(dish);
	}
}
