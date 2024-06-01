package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Setmeal;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
}
