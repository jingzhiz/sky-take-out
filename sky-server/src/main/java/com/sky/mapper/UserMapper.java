package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.User;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

	/**
	 * 根据 openid 查询用户
	 * @param openid
	 * @return
	 */

	@Select("select * from user where openid = #{openid}")
	User getByOpenid(String openid);

	/**
	 * 插入用户
	 * @param user
	 */
	void insert(User user);
}
