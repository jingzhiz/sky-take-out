package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 自定义切面类
 * 实现公共字段自动填充逻辑
 */
@Slf4j
@Aspect
@Component
public class AutoFillAspect {
	/**
	 * 切入点，拦截所有 mapper 操作中的 insert/update 方法
	 */
	@Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
	public void autoFillPointCut() {}

	@Before("autoFillPointCut()")
	public void autoFill(JoinPoint joinPoint) {
		log.info("开始进行公共字段的自动填充...");

		// 获取到当前被拦截的方法上的数据库操作类型
		MethodSignature signature = (MethodSignature) joinPoint.getSignature(); // 方法签名对象
		AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class); // 方法的注解对象
		OperationType opType = autoFill.value(); // 获取数据库的操作类型

		// 获取到当前被拦截的方法的参数 -- 实体对象
		Object[] args = joinPoint.getArgs();
		if (args == null || args.length == 0) {
			return;
		}

		Object entity = args[0];

		// 准备赋值的数据
		Long currentUserId = BaseContext.getCurrentId();
		LocalDateTime currentTime = LocalDateTime.now();

		// 根据当前不同的操作类型，为对应的属性通过反射来赋值
		if (opType == OperationType.INSERT) {
			try {
				Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
				Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
				Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
				Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

				// 通过反射为对象赋值
				setCreateTime.invoke(entity, currentTime);
				setCreateUser.invoke(entity, currentUserId);
				setUpdateTime.invoke(entity, currentTime);
				setUpdateUser.invoke(entity, currentUserId);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else if (opType == OperationType.UPDATE){
			try {
				Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
				Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

				// 通过反射为对象赋值
				setUpdateTime.invoke(entity, currentTime);
				setUpdateUser.invoke(entity, currentUserId);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
