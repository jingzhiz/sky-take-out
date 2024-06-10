package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
	@Autowired
	private ShoppingCartMapper shoppingCartMapper;
	@Autowired
	private DishMapper dishMapper;
	@Autowired
	private SetmealMapper setmealMapper;

	/**
	 * 添加购物车
	 * @param shoppingCartDTO
	 * @return
	 */
	public void add(ShoppingCartDTO shoppingCartDTO) {
		ShoppingCart shoppingCart = new ShoppingCart();

		BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);


		Long userId = BaseContext.getCurrentId();
		shoppingCart.setUserId(userId);

		List<ShoppingCart> cartList = shoppingCartMapper.list(shoppingCart);

		if (cartList == null || cartList.size() == 0) {
			Long dishId = shoppingCartDTO.getDishId();

			if (dishId != null) {
				//添加到购物车中的是菜品
				Dish dish = dishMapper.getById(dishId);

				shoppingCart.setName(dish.getName());
				shoppingCart.setAmount(dish.getPrice());
				shoppingCart.setImage(dish.getImage());
			} else {
				//添加到购物车中的是套餐
				Setmeal setmeal = setmealMapper.getById(shoppingCartDTO.getSetmealId());

				shoppingCart.setName(setmeal.getName());
				shoppingCart.setAmount(setmeal.getPrice());
				shoppingCart.setImage(setmeal.getImage());
			}
			shoppingCart.setNumber(1);
			shoppingCart.setCreateTime(LocalDateTime.now());

			shoppingCartMapper.insert(shoppingCart);

			return;
		}

		ShoppingCart cart = cartList.get(0);

		cart.setNumber(cart.getNumber() + 1);

		shoppingCartMapper.updateNumberById(cart);
	}


	/**
	 * 减少购物车
	 * @param shoppingCartDTO
	 * @return
	 */
	public void sub(ShoppingCartDTO shoppingCartDTO) {
		ShoppingCart shoppingCart = new ShoppingCart();

		BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);

		List<ShoppingCart> cartList = shoppingCartMapper.list(shoppingCart);

		if (cartList != null || cartList.size() != 0) {
			ShoppingCart cart = cartList.get(0);

			if (cart.getNumber() == 1) {
				shoppingCartMapper.delete(cart);
			} else {
				cart.setNumber(cart.getNumber() - 1);
				shoppingCartMapper.updateNumberById(cart);
			}
		}
	}

	/**
	 * 查询购物车
	 */
	public List<ShoppingCart> list() {
		Long userId = BaseContext.getCurrentId();
		ShoppingCart shoppingCart = new ShoppingCart();
		shoppingCart.setUserId(userId);

		return shoppingCartMapper.list(shoppingCart);
	}

	/**
	 * 清空购物车
	 */
	public void clean() {
		Long userId = BaseContext.getCurrentId();

		shoppingCartMapper.clean(userId);
	}
}
