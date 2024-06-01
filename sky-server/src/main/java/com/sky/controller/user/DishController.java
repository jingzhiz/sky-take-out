package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Api(tags = "小程序端菜品相关接口")
@RestController("userDishController")
@RequestMapping("/user/dish")
public class DishController {
	@Autowired
	DishService dishService;

	/**
	 * 根据分类查询菜品
	 * @param categoryId
	 * @return
	 */
	@GetMapping("/list")
	@ApiOperation("小程序端根据分类查询菜品")
	Result list(Long categoryId) {
		log.info("小程序端根据分类查询菜品，{}", categoryId);

		Dish dish = Dish.builder()
				.categoryId(categoryId)
				.status(StatusConstant.ENABLE)
				.build();

		List<DishVO> list = dishService.listWithFlavor(dish);

		return Result.success(list);
	}
}
