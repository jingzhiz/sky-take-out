package com.sky.mapper;

import com.sky.entity.SetmealDish;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

	/**
	 * 根据菜品id查询套餐id
	 * @param dishIds
	 * @return
	 */
	List<Long> getSetmealIdsByDishIds(List<Long> dishIds);

	/**
	 * 新增套餐内菜品
	 * @param setmealDishs
	 */
	void insertBatch(List<SetmealDish> setmealDishs);

	List<SetmealDish> getBySetmealId(Long setmealId);

	/**
	 * 根据setmealId删除所关联的菜品
	 * @param setmealIds
	 */
	void deleteBySetmealIds(List<Long> setmealIds);
}
