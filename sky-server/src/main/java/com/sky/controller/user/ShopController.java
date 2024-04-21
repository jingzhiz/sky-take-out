package com.sky.controller.user;

import com.sky.result.Result;
import com.sky.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(tags = "店铺相关接口")
@RestController("userShopController")
@RequestMapping("/user/shop")
public class ShopController {
	@Autowired
	ShopService shopService;

	/**
	 * 查询店铺状态
	 */
	@GetMapping("/status")
	@ApiOperation("查询店铺状态")
	public Result<Integer> getStatus() {
		log.info("查询店铺状态");

		Integer status = shopService.getStatus();

		return Result.success(status);
	}

}
