package com.sky.controller.user;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.properties.JwtProperties;
import com.sky.result.Result;
import com.sky.service.UserService;
import com.sky.utils.JwtUtil;
import com.sky.vo.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user/user")
@Api(tags = "C端用户相关接口")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private JwtProperties jwtProperties;


	/**
	 * 微信登录
	 * @param userLoginDTO
	 * @return
	 */
	@PostMapping("/login")
	@ApiOperation(value = "用户微信登录")
	public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
		log.info("用户微信登录，{}", userLoginDTO);

		User user = userService.wxLogin(userLoginDTO);

		// 生成 jwt 令牌
		Map<String, Object> claims = new HashMap<>();

		claims.put(JwtClaimsConstant.USER_ID, user.getId());

		String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);

		UserLoginVO userLoginVO = UserLoginVO.builder()
				.id(user.getId())
				.openid(user.getOpenid())
				.token(token)
				.build();

		return Result.success(userLoginVO);
	}
}
