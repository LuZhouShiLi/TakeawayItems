package com.ustc.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ustc.common.R;
import com.ustc.entity.Employee;
import com.ustc.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    // 加了@RequestBody 才能接收到前端发送来的JSON数据
    // 前端发送过来的数据，要和Employee实体类一一对应，否则无法封装
    // HttpServletRequest 是为了登录成功之后，把Employee对象的id存到Session中，便于后期获取当前登录用户（用request的getSession）
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        /**
         *
         *
         * 5查看员工状态，如果为已禁用状态，则返回员工已禁用结果
         * 6登录成功，将员工id存入session并返回登录成功
         */
        // 1、将页面提交的密码进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        // 2、根据页面提交的用户名查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        // 因为数据库中username是唯一的，所以这里调用getOne方法
        Employee emp = employeeService.getOne(queryWrapper);

        // 3、如果没有查询到则返回登录失败
        if(emp == null){
            return R.error("登录失败！");
        }

        // 4、密码比对，如果不一致则返回登录失败
        if(!emp.getPassword().equals(password)){
            return R.error("登录失败！");
        }

        // 5、查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if(emp.getStatus() == 0){
            return R.error("账号已禁用");
        }
        // 6、登录成功，将员工id存入session并返回登录成功  方便浏览器读取
        request.getSession().setAttribute("employee",emp.getId());

        return R.success(emp);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        // 清理session中保存的当前登录员工的id
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

}