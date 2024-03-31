package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
	/**
	 * 新增口味
	 * @param flavors
	 */
	void insertBatch(List<DishFlavor> flavors);

	/**
	 * 通过菜品删除口味
	 * @param dishIds
	 */
	void deleteByDishIds(List<Long> dishIds);

	/**
	 * 通过菜品id查询口味
	 * @param dishId
	 * @return
	 */
	List<DishFlavor> getByDishId(Long dishId);
}
