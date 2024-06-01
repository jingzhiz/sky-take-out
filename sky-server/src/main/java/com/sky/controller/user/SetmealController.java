package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Setmeal;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Api(tags = "小程序端套餐相关接口")
@RestController("userSetmealController")
@RequestMapping("/user/setmeal")
public class SetmealController {

	@Autowired
	SetmealService setmealService;

	/**
	 * 根据分类查询套餐
	 * @param categoryId
	 * @return
	 */
	@GetMapping("/list")
	@ApiOperation("小程序端查询套餐列表")
	Result<List<SetmealVO>> list(Long categoryId) {
		log.info("小程序端查询套餐列表，{}", categoryId);

		Setmeal setmeal = Setmeal.builder()
				.categoryId(categoryId)
				.status(StatusConstant.ENABLE)
				.build();

		List<SetmealVO> setmealList = setmealService.list(setmeal);

		return Result.success(setmealList);
	}

	/**
	 * 根据套餐id查询包含的菜品列表
	 * @param id
	 * @return
	 */
	@GetMapping("/dish/{id}")
	@ApiOperation("小程序端查询套餐内菜品")
	Result<List<DishItemVO>> getSetmealDish(@PathVariable Long id) {
		log.info("小程序端查询套餐内菜品，{}", id);

		List<DishItemVO> list = setmealService.getDishItemById(id);

		return Result.success(list);
	}
}
