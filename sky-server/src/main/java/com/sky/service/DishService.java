package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;
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

	/**
	 * 根据id查询菜品
	 * @param id
	 * @return
	 */
	DishVO getById(Long id);

	/**
	 * 根据 categoryId 查询菜品列表
	 * @param categoryId
	 */
	List<DishVO> list(Long categoryId);

	/**
	 * 修改菜品数据
	 * @param dishDTO
	 * @return
	 */
	void update(DishDTO dishDTO);

	/**
	 * 修改菜品启停售状态
	 * @param status
	 * @param id
	 * @return
	 */
	void updateStatus(Integer status, Long id);
}
