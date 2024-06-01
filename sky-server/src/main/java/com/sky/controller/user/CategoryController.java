package com.sky.controller.user;

import com.sky.entity.Category;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Api(tags = "小程序端分类相关接口")
@RestController("userCategoryController")
@RequestMapping("/user/category")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	/**
	 * 查询分类
	 * @param type
	 * @return
	 */
	@GetMapping("/list")
	@ApiOperation("小程序端查询分类列表")
	Result<List<Category>> list(Integer type) {
		log.info("小程序端查询分类列表，{}", type);

		List<Category> list = categoryService.list(type);

		return Result.success(list);
	}
}
