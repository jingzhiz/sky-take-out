package com.sky.controller.admin;

import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/admin/setmeal")
@Api(tags = "套餐管理")
public class SetmealController {
	@Autowired
	SetmealService setmealService;

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
