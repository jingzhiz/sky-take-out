package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealMapper {
	/**
	 * 分页查询套餐
	 * @param setmealPageQueryDTO
	 * @return
	 */
	Page<SetmealVO> query(SetmealPageQueryDTO setmealPageQueryDTO);

	/**
	 * 根据分类id查询菜品数量
	 * @param categoryId
	 * @return
	 */
	@Select("select count(id) from setmeal where category_id = #{categoryId}")
	Integer countByCategoryId(Long categoryId);

	/**
	 * 新增套餐
	 * @param setmeal
	 * @return
	 */
	@AutoFill(value = OperationType.INSERT)
	void create(Setmeal setmeal);

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
	@Select("select * from setmeal where id = #{id}")
	Setmeal getById(Long id);

	/**
	 * 修改套餐
	 * @param setmeal
	 * @return
	 */
	@AutoFill(value = OperationType.UPDATE)
	void update(Setmeal setmeal);
}
