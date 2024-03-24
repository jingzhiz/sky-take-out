package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {

	/**
	 * 新增分类
	 * @param category
	 * @return
	 */
	@Insert(
			"insert into category" +
			"(type, name, sort, status, create_time, update_time, create_user, update_user)" +
			"values" + " " +
			"(#{type}, #{name}, #{sort}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})"
	)
	@AutoFill(value = OperationType.INSERT)
	void create(Category category);

	/**
	 * 删除分类
	 * @param id
	 * @return
	 */
	@Delete("delete from category where id = #{id}")
	void deleteById(Long id);

	/**
	 * 修改分类
	 * @param category
	 * @return
	 */
	@AutoFill(value = OperationType.UPDATE)
	void update(Category category);

	/**
	 * 分页查询分类
	 * @param categoryPageQueryDTO
	 * @return
	 */
	Page<Category> query(CategoryPageQueryDTO categoryPageQueryDTO);

	/**
	 * 根据类型查询分类列表
	 * @param type
	 * @return
	 */
	List<Category> list(Integer type);
}
