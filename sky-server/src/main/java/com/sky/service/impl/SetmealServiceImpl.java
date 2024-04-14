package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {
	@Autowired
	SetmealMapper setmealMapper;
	@Autowired
	SetmealDishMapper setmealDishMapper;
	@Autowired
	DishMapper dishMapper;

	/**
	 * 分页查询套餐
	 * @param setmealPageQueryDTO
	 * @return
	 */
	public PageResult query(SetmealPageQueryDTO setmealPageQueryDTO) {
		PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());

		Page<SetmealVO> page = setmealMapper.query(setmealPageQueryDTO);

		Long total = page.getTotal();
		List<SetmealVO> records = page.getResult();

		return new PageResult(total, records);
	}

	/**
	 * 新增套餐
	 * @param setmealDTO
	 * @return
	 */
	@Transactional
	public void create(SetmealDTO setmealDTO) {
		Setmeal setmeal = new Setmeal();

		BeanUtils.copyProperties(setmealDTO, setmeal);

		setmeal.setStatus(StatusConstant.DISABLE);

		setmealMapper.create(setmeal);

		Long id = setmeal.getId();

		List<SetmealDish> setmealDishs = setmealDTO.getSetmealDishes();
		if (setmealDishs != null && setmealDishs.size() > 0) {
			for(SetmealDish setmealDish : setmealDishs) {
				setmealDish.setSetmealId(id);
			}

			setmealDishMapper.insertBatch(setmealDishs);
		}

	}

	/**
	 * 删除套餐
	 * @param ids
	 * @return
	 */
	@Transactional
	public void delete(List<Long> ids) {
		for (Long id :ids) {
			Setmeal setmeal = setmealMapper.getById(id);
			if (setmeal.getStatus() == StatusConstant.ENABLE) {
				throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
			}
		}

		setmealMapper.delete(ids);

		setmealDishMapper.deleteBySetmealIds(ids);
	}

	/**
	 * 根据id查询套餐
	 * @param id
	 * @return
	 */
	public SetmealVO getById(Long id) {
		Setmeal setmeal = setmealMapper.getById(id);

		SetmealVO setmealVO = new SetmealVO();

		BeanUtils.copyProperties(setmeal, setmealVO);

		List<SetmealDish> setmealDishs = setmealDishMapper.getBySetmealId(setmealVO.getId());

		setmealVO.setSetmealDishes(setmealDishs);

		return setmealVO;
	}

	/**
	 * 修改套餐
	 * @param setmealDTO
	 * @return
	 */
	@Transactional
	public void update(SetmealDTO setmealDTO) {
		Setmeal setmeal = new Setmeal();
		BeanUtils.copyProperties(setmealDTO, setmeal);
		setmealMapper.update(setmeal);

		List<Long> setmealIds = new ArrayList();

		Long setmeamId = setmeal.getId();
		setmealIds.add(setmeamId);

		setmealDishMapper.deleteBySetmealIds(setmealIds);
		List<SetmealDish> setmealDishs = setmealDTO.getSetmealDishes();
		if (setmealDishs != null && setmealDishs.size() > 0) {
			for (SetmealDish setmealDish : setmealDishs) {
				setmealDish.setSetmealId(setmeamId);
			}
			setmealDishMapper.insertBatch(setmealDishs);
		}
	}

	/**
	 * 修改套餐状态
	 * @param id
	 * @param status
	 * @return
	 */
	public void updateStatus(Long id, Integer status) {
		if (status == StatusConstant.ENABLE) {
			List<SetmealDish> setmealDishs = setmealDishMapper.getBySetmealId(id);
			for (SetmealDish setmealDish : setmealDishs) {
				Long dishId = setmealDish.getDishId();
				Dish dish = dishMapper.getById(dishId);
				if (dish.getStatus() == StatusConstant.DISABLE) {
					throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ENABLE_FAILED);
				}
			}
		}

		Setmeal setmeal = Setmeal.builder()
				.id(id)
				.status(status)
				.build();

		setmealMapper.update(setmeal);
	}
}
