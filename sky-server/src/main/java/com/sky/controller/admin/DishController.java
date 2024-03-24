package com.sky.controller.admin;

import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品管理")
public class DishController {
	@Autowired
	DishService dishService;

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
