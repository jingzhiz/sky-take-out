package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryMapper categoryMapper;
	@Autowired
	DishMapper dishMapper;
	@Autowired
	SetmealMapper setmealMapper;

	/**
	 * 新增分类
	 * @param categoryDTO
	 * @return
	 */
	public void create(CategoryDTO categoryDTO) {
		Category category = new Category();
		BeanUtils.copyProperties(categoryDTO, category);

		// 分类状态刚创建好默认是禁用
		category.setStatus(StatusConstant.DISABLE);

		categoryMapper.create(category);
	}

	/**
	 * 删除分类
	 * @param id
	 * @return
	 */
	public void deleteById(Long id) {
		//查询当前分类是否关联了菜品，如果关联了就抛出业务异常
		if (dishMapper.countByCategoryId(id) > 0) {
			//当前分类下有菜品，不能删除
			throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
		}

		//查询当前分类是否关联了套餐，如果关联了就抛出业务异常
		if(setmealMapper.countByCategoryId(id) > 0){
			//当前分类下有套餐，不能删除
			throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
		}

		categoryMapper.deleteById(id);
	}

	/**
	 * 修改分类
	 * @param categoryDTO
	 * @return
	 */
	public void update(CategoryDTO categoryDTO) {
		Category category = new Category();
		BeanUtils.copyProperties(categoryDTO, category);

		categoryMapper.update(category);
	}

	/**
	 * 修改分类状态
	 * @param id
	 * @param status
	 * @return
	 */
	public void updateStatus(Long id, Integer status) {
		Category category = Category.builder()
				.id(id)
				.status(status)
				.build();

		categoryMapper.update(category);
	}

	/**
	 * 分页查询分类
	 * @param categoryPageQueryDTO
	 * @return
	 */
	public PageResult query(CategoryPageQueryDTO categoryPageQueryDTO) {
		PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());

		Page<Category> page = categoryMapper.query(categoryPageQueryDTO);

		long total = page.getTotal();
		List<Category> records = page.getResult();

		return new PageResult(total, records);
	}

	/**
	 * 根据类型查询分类列表
	 * @param type
	 * @return
	 */
	public List<Category> list(Integer type) {
		return categoryMapper.list(type);
	}
}
