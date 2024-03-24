package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

	/**
	 * 新增分类
	 * @param categoryDTO
	 * @return
	 */
	void create(CategoryDTO categoryDTO);

	/**
	 * 删除分类
	 * @param id
	 * @return
	 */
	void deleteById(Long id);

	/**
	 * 修改分类
	 * @param categoryDTO
	 * @return
	 */
	void update(CategoryDTO categoryDTO);

	/**
	 * 修改分类状态
	 * @param id
	 * @param status
	 * @return
	 */
	void updateStatus(Long id, Integer status);

	/**
	 * 分页查询分类
	 * @param categoryPageQueryDTO
	 * @return
	 */
	PageResult query(CategoryPageQueryDTO categoryPageQueryDTO);

	/**
	 * 根据类型查询分类列表
	 * @param type
	 * @return
	 */
	List<Category> list(Integer type);
}
