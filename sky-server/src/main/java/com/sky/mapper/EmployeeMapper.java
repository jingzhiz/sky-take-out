package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * 创建一个新员工
     * @param employee
     * @return
     */
    @Select(
        "insert into employee " +
            "(name, username, password, phone, sex, id_number, create_time, update_time, create_user, update_user)" +
        "values" +
            "(#{name}, #{username}, #{password}, #{phone}, #{sex}, #{idNumber}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})"
    )
    @AutoFill(value = OperationType.INSERT)
    Employee insert(Employee employee);

    /**
     * 分页查询员工
     * @param employeePageQueryDTO
     * @return
     */
    Page<Employee> query(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 修改员工数据
     * @param employee
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Employee employee);

    /**
     * 根据 id 查询员工详情
     * @param id
     */
    @Select("select * from employee where id = #{id}")
    Employee getById(Long id);
}
