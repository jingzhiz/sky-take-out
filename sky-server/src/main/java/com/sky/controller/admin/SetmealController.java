package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/setmeal")
@Api(tags = "套餐管理")
public class SetmealController {
	@Autowired
	SetmealService setmealService;

	/**
	 * 新增套餐
	 * @param setmealDTO
	 * @return
	 */
	@PostMapping
	@ApiOperation(value = "新增套餐")
	@CacheEvict(cacheNames = "setmealCache", key = "#setmealDTO.categoryId")
	public Result create(@RequestBody SetmealDTO setmealDTO) {
		log.info("新增套餐，{}", setmealDTO);

		setmealService.create(setmealDTO);

		return Result.success();
	}

	/**
	 * 删除套餐
	 * @param ids
	 * @return
	 */
	@DeleteMapping
	@ApiOperation(value = "删除套餐")
	@CacheEvict(cacheNames = "setmealCache", allEntries = true)
	public Result delete(@RequestParam List<Long> ids) {
		log.info("删除套餐，{}", ids);

		setmealService.delete(ids);

		return Result.success();
	}

	/**
	 * 根据id查询套餐
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	@ApiOperation(value = "根据id查询套餐")
	Result<SetmealVO> getById(@PathVariable Long id) {
		log.info("根据id查询套餐，{}", id);

		SetmealVO setmeal = setmealService.getById(id);

		return Result.success(setmeal);
	}

	/**
	 * 修改套餐
	 * @param setmealDTO
	 * @return
	 */
	@PutMapping
	@ApiOperation(value = "修改套餐")
	@CacheEvict(cacheNames = "setmealCache", allEntries = true)
	public Result update(@RequestBody SetmealDTO setmealDTO) {
		log.info("修改套餐，{}", setmealDTO);

		setmealService.update(setmealDTO);

		return Result.success();
	}

	/**
	 * 修改套餐状态
	 * @param id
	 * @param status
	 * @return
	 */
	@PostMapping("/status/{status}")
	@ApiOperation(value = "修改套餐状态")
	@CacheEvict(cacheNames = "setmealCache", allEntries = true)
	public Result updateStatus(Long id, @PathVariable Integer status) {
		log.info("修改套餐状态，{}，{}", id, status);

		setmealService.updateStatus(id, status);

		return Result.success();
	}

	/**
	 * 分页查询套餐
	 * @param
	 * @return
	 */
	@GetMapping("/page")
	@ApiOperation(value = "分页查询套餐")
	Result<PageResult> query(SetmealPageQueryDTO setmealPageQueryDTO) {
		log.info("分页查询套餐，{}", setmealPageQueryDTO);

		PageResult pageResult = setmealService.query(setmealPageQueryDTO);

		return Result.success(pageResult);
	}
}
