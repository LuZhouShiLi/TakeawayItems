package com.ustc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ustc.entity.Employee;
import com.ustc.mapper.EmployeeMapper;
import com.ustc.service.EmployeeService;
import org.springframework.stereotype.Service;

// 这里的泛型需要指定两个 一个是mapper 一个是实体类
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
