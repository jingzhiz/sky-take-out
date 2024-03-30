package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DishMapper {
	/**
	 * 分页查询菜品
	 * @param dishPageQueryDTO
	 * @return
	 */
	Page<DishVO> query(DishPageQueryDTO dishPageQueryDTO);

	/**
	 * 根据分类id查询菜品数量
	 * @param categoryId
	 * @return
	 */
	@Select("select count(id) from dish where category_id = #{categoryId}")
	Integer countByCategoryId(Long categoryId);

	/**
	 * 新增菜品
	 * @param dish
	 */
	@AutoFill(value = OperationType.INSERT)
	void create(Dish dish);
}
