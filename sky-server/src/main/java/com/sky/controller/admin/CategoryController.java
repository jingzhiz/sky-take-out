package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/category")
@Api(tags = "分类管理")
public class CategoryController {
	@Autowired
	CategoryService categoryService;

	/**
	 * 新增分类
	 * @param categoryDTO
	 * @return
	 */
	@PostMapping
	@ApiOperation(value = "新增分类")
	Result create(@RequestBody CategoryDTO categoryDTO) {
		log.info("新增分类，{}", categoryDTO);

		categoryService.create(categoryDTO);

		return Result.success();
	}

	/**
	 * 删除分类
	 * @param id
	 * @return
	 */
	@DeleteMapping
	@ApiOperation(value = "删除分类")
	Result deleteById(Long id) {
		log.info("根据ID删除分类，{}", id);

		categoryService.deleteById(id);

		return Result.success();
	}

	/**
	 * 修改分类
	 * @param categoryDTO
	 * @return
	 */
	@PutMapping
	@ApiOperation(value = "修改分类")
	Result update(@RequestBody CategoryDTO categoryDTO) {
		log.info("修改分类，{}", categoryDTO);

		categoryService.update(categoryDTO);

		return Result.success();
	}

	/**
	 * 修改分类状态
	 * @param status
	 * @return
	 */
	@PostMapping("/status/{status}")
	@ApiOperation(value = "修改分类")
	Result updateStatus(@PathVariable Integer status, Long id) {
		log.info("修改分类状态，{}", status);

		categoryService.updateStatus(id, status);

		return Result.success();
	}

	/**
	 * 分类管理分页查询
	 * @param categoryPageQueryDTO
	 * @return
	 */
	@GetMapping("/page")
	@ApiOperation(value = "分页查询分类")
	Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO) {
		log.info("分类管理分页查询，{}", categoryPageQueryDTO);

		PageResult pageResult = categoryService.query(categoryPageQueryDTO);

		return Result.success(pageResult);
	}

	/**
	 * 根据类型分类列表查询
	 * @param type
	 * @return
	 */
	@GetMapping("/list")
	@ApiOperation(value = "根据类型分类列表查询")
	Result<List<Category>> list(Integer type) {
		log.info("根据类型分类列表查询，{}", type);

		List<Category> list = categoryService.list(type);

		return Result.success(list);
	}
}
