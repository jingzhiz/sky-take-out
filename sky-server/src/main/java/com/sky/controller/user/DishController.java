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
import org.springframework.data.redis.core.RedisTemplate;
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
	@Autowired
	RedisTemplate redisTemplate;

	/**
	 * 根据分类查询菜品
	 * @param categoryId
	 * @return
	 */
	@GetMapping("/list")
	@ApiOperation("小程序端根据分类查询菜品")
	Result list(Long categoryId) {
		log.info("小程序端根据分类查询菜品，{}", categoryId);

		// 查询 redis 中是否存在菜品数据
		String key = "dish_" + categoryId;
		List<DishVO> list = (List<DishVO>) redisTemplate.opsForValue().get(key);

		// 如果存在则直接返回缓存数据，无需查询数据库
		if (list != null && list.size() > 0) {
			return Result.success(list);
		}

		// 如果不存在，查询数据库，将数据放入 redis 中缓存
		Dish dish = Dish.builder()
				.categoryId(categoryId)
				.status(StatusConstant.ENABLE)
				.build();

		list = dishService.listWithFlavor(dish);

		redisTemplate.opsForValue().set(key, list);

		return Result.success(list);
	}
}
