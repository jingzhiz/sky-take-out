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
}
