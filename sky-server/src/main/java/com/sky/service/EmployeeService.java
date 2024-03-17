package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

import java.util.ArrayList;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 新增员工
     * @param employeeDTO
     * @return
     */
    void create(EmployeeDTO employeeDTO);

    /**
     * 分页查询员工
     * @param employeePageQueryDTO
     * @return
     */
    PageResult query(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 修改员工状态
     * @param id
     * @param status
     * @return
     */
    void updateStatus(Long id, Integer status);

    /**
     * 查询员工详情
     * @param id
     * @return
     */
    Employee getById(Long id);

    /**
     * 修改员工
     * @param employeeDTO
     * @return
     */
    void update(EmployeeDTO employeeDTO);
}
