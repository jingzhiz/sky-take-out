package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品管理")
public class DishController {
	@Autowired
	DishService dishService;

	/**
	 * 新增菜品
	 * @param dishDTO
	 * @return
	 */
	@PostMapping
	@ApiOperation(value = "新增菜品")
	Result create(@RequestBody DishDTO dishDTO) {
		log.info("新增菜品，{}", dishDTO);

		dishService.create(dishDTO);

		return Result.success();
	}

	/**
	 * 删除菜品
	 * @param ids
	 * @return
	 */
	@DeleteMapping
	@ApiOperation(value = "删除菜品")
	Result delete(@RequestBody List<Long> ids) {
		log.info("删除菜品，{}", ids);

		dishService.delete(ids);

		return Result.success();
	}

	/**
	 * 分页查询菜品
	 * @param
	 * @return
	 */
	@GetMapping("/page")
	@ApiOperation(value = "分页查询菜品")
	Result<PageResult> query(DishPageQueryDTO dishPageQueryDTO) {
		log.info("分页查询菜品，{}", dishPageQueryDTO);

		PageResult pageResult = dishService.query(dishPageQueryDTO);

		return Result.success(pageResult);
	}
}
