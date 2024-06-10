package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShoppingCartService {

	/**
	 * 添加购物车
	 * @param shoppingCartDTO
	 * @return
	 */
	void add(ShoppingCartDTO shoppingCartDTO);

	/**
	 * 减少购物车
	 * @param shoppingCartDTO
	 * @return
	 */
	void sub(ShoppingCartDTO shoppingCartDTO);

	/**
	 * 查询购物车
	 */
	List<ShoppingCart> list();

	/**
	 * 清空购物车
	 */
	void clean();
}
