package com.sky.service;

import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import org.springframework.stereotype.Service;

@Service
public interface SetmealService {
	/**
	 * 分页查询套餐
	 * @param setmealPageQueryDTO
	 * @return
	 */
	PageResult query(SetmealPageQueryDTO setmealPageQueryDTO);
}