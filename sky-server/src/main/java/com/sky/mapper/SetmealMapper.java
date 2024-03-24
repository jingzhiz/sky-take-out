package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SetmealMapper {
	/**
	 * 分页查询套餐
	 * @param setmealPageQueryDTO
	 * @return
	 */
	Page<Setmeal> query(SetmealPageQueryDTO setmealPageQueryDTO);

	/**
	 * 根据分类id查询菜品数量
	 * @param categoryId
	 * @return
	 */
	@Select("select count(id) from setmeal where category_id = #{categoryId}")
	Integer countByCategoryId(Long categoryId);
}
