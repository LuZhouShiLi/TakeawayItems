package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.dto.SetmealDto;
import com.itheima.reggie.entity.Setmeal;
import com.itheima.reggie.entity.SetmealDish;
import com.itheima.reggie.mapper.SetmealMapper;
import com.itheima.reggie.service.SetmealDishService;
import com.itheima.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 新增套餐服务  一个是套餐表 另一个是套餐和菜品的关系表
 */
@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Autowired
    private SetmealDishService setmealDishService;
    /**
     * 新增套餐 同时需要保存套餐和菜品的关联关系
     * 将套餐的基本信息和套餐的关联的菜品信息一起保存下来  传入DTO页面数对象
     * @param setmealDto
     */

    // 涉及到两张表  需要事务操作
    @Transactional
    public void saveWithDish(SetmealDto setmealDto){

        // 保存套餐的基本信息  直接使用DTO对象 因为DTO对象继承SetMeal
        this.save(setmealDto);


        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();

        // 给每一个菜品赋值id
        setmealDishes.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(setmealDishes);// 批量保存
    }
}
