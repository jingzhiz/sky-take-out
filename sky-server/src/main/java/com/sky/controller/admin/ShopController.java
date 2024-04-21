package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(tags = "店铺相关接口")
@RestController("adminShopController")
@RequestMapping("/admin/shop")
public class ShopController {
	@Autowired
	ShopService shopService;

	/**
	 * 修改店铺状态
	 * @param status
	 */
	@PutMapping("/{status}")
	@ApiOperation("修改店铺状态")
	public Result updateStatus(@PathVariable Integer status) {
		log.info("修改店铺状态，{}", status == 1 ? "营业中" : "打样中");

		shopService.updateStatus(status);

		return Result.success();
	}

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
