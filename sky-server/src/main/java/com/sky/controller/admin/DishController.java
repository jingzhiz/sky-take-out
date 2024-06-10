package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品管理")
public class DishController {
	@Autowired
	DishService dishService;
	@Autowired
	RedisTemplate redisTemplate;

	private void cleanCache(String pattern) {
		Set keys = redisTemplate.keys(pattern);
		redisTemplate.delete(keys);
	}

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
	Result delete(@RequestParam List<Long> ids) { // 将 1,2,3 转成 [1, 2, 3]
		log.info("删除菜品，{}", ids);

		dishService.delete(ids);

		cleanCache("dish_*");

		return Result.success();
	}

	/**
	 * 修改菜品数据
	 * @param dishDTO
	 * @reutn
	 */
	@PutMapping
	@ApiOperation(value = "修改菜品")
	Result update(@RequestBody DishDTO dishDTO) {
		log.info("修改菜品，{}", dishDTO);

		dishService.update(dishDTO);

		cleanCache("dish_*");

		return Result.success();
	}

	/**
	 * 修改菜品启停售状态
	 * @param status
	 * @param id
	 * @return
	 */
	@PostMapping("/status/{status}")
	@ApiOperation(value = "修改菜品启停售状态")
	Result updateStatus(@PathVariable Integer status, Long id) {
		log.info("修改菜品启停售状态，{}, {}", status, id);

		dishService.updateStatus(status, id);

		cleanCache("dish_*");

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

	@GetMapping("/{id}")
	@ApiOperation(value = "根据id查询菜品")
	Result<DishVO> getById(@PathVariable Long id) {
		log.info("根据id查询菜品，{}", id);

		DishVO dishVO = dishService.getById(id);

		return Result.success(dishVO);
	}

	@GetMapping("/list")
	@ApiOperation(value = "根据categoryId查询菜品列表")
	Result<List<Dish>> list(Long categoryId) {
		log.info("根据id查询菜品，{}", categoryId);

		List<Dish> dishList = dishService.list(categoryId);

		return Result.success(dishList);
	}
}
