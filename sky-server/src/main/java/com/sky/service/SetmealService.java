package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SetmealService {
	/**
	 * 分页查询套餐
	 * @param setmealPageQueryDTO
	 * @return
	 */
	PageResult query(SetmealPageQueryDTO setmealPageQueryDTO);

	/**
	 * 新增套餐
	 * @param setmealDTO
	 * @return
	 */
	void create(SetmealDTO setmealDTO);

	/**
	 * 删除套餐
	 * @param ids
	 * @return
	 */
	void delete(List<Long> ids);

	/**
	 * 根据id查询套餐
	 * @param id
	 * @return
	 */
	SetmealVO getById(Long id);

	/**
	 * 修改套餐
	 * @param setmealDTO
	 * @return
	 */
	void update(SetmealDTO setmealDTO);

	/**
	 * 修改套餐状态
	 * @param id
	 * @param status
	 * @return
	 */
	void updateStatus(Long id, Integer status);

	/**
	 * 条件查询套餐
	 * @param setmeal
	 * @return
	 */
	List<SetmealVO> list(Setmeal setmeal);
}