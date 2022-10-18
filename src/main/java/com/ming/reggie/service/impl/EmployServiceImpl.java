package com.ming.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ming.reggie.entity.Employee;
import com.ming.reggie.mapper.EmployeeMapper;
import com.ming.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
