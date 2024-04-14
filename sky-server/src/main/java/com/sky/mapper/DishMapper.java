package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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

	/**
	 * 根据 id 查询菜品
	 * @param id
	 */
	@Select("select * from dish where id = #{id}")
	Dish getById(Long id);

	/**
	 * 根据 categoryId 查询菜品列表
	 * @param categoryId
	 */
	@Select("select * from dish where category_id = #{categoryId}")
	List<DishVO> list(Long categoryId);

	void delete(List<Long> ids);

	@AutoFill(value = OperationType.UPDATE)
	void update(Dish dish);
}
