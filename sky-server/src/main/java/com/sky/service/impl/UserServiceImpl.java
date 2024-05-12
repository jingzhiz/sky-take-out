package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

@Service
public class UserServiceImpl implements UserService {
	// 微信服务接口地址
	public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";
	// 微信登录接口授权类型
	public static final String GRANT_TYPE = "authorization_code";

	@Autowired
	private WeChatProperties weChatProperties;
	@Autowired
	private UserMapper userMapper;

	/**
	 * 获取 openid
	 * @param code
	 * @return
	 */
	private String getOpenid(String code) {
		HashMap<String, String> requestParams = new HashMap<>();

		// 写入参数
		requestParams.put("appid", weChatProperties.getAppid());
		requestParams.put("secret", weChatProperties.getSecret());
		requestParams.put("js_code", code);
		requestParams.put("grant_type", GRANT_TYPE);

		// 调用接口
		String json = HttpClientUtil.doGet(WX_LOGIN, requestParams);

		JSONObject jsonObject = JSON.parseObject(json);
		return jsonObject.getString("openid");
	}


	/**
	 * 微信登录
	 * @param userLoginDTO
	 * @return
	 */
	public User wxLogin(UserLoginDTO userLoginDTO) {
		// 调用微信登录接口，获取当前用户的 openid
		String openid = getOpenid(userLoginDTO.getCode());

		// 判断 openid 是否为空，为空表示失败，抛出异常
		if (openid == null) {
			throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
		}

		// 判断当前是否为新用户，如果是新用户，要将用户进行注册
		User user = userMapper.getByOpenid(openid);

		if (user == null) {
			user = User.builder()
					.openid(openid)
					.createTime(LocalDateTime.now())
					.build();

			userMapper.insert(user);
		}

		// 返回对象
		return user;
	}
}
