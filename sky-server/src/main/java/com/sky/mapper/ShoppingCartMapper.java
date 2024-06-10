package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {
	// 购物车查询
	List<ShoppingCart> list(ShoppingCart shoppingCart);


	// 更新购物车中数量
	@Update("update shopping_cart set number = #{number} where id = #{id}")
	void updateNumberById(ShoppingCart shoppingCart);

	// 购物车中插入数据
	@Insert("insert into shopping_cart " +
	"(name, image, user_id, dish_id, setmeal_id, dish_flavor, amount, create_time) " +
			"values " +
	"(#{name}, #{image}, #{userId}, #{dishId}, #{setmealId}, #{dishFlavor}, #{amount}, #{createTime})"
	)
	void insert(ShoppingCart shoppingCart);

	// 清空购物车中数据
	@Delete("delete from shopping_cart where user_id = #{userId}")
	void clean(Long userId);
}
