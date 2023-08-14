package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.DishFlavor;
import com.itheima.reggie.mapper.DishMapper;
import com.itheima.reggie.service.DishFlavorService;
import com.itheima.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j

public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * 新增菜品 同时保存口味数据
     * @param dishDto
     */
    @Override
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        // 将DishDTo中的数据 存入Dish 和DishFlvor两张表中


        // 将菜品的基本信息 保存在菜品表
        this.save(dishDto);

//        dishFlavorService.save(dishDto);

        // 取出菜品id
        Long dishId = dishDto.getId();

        // 取出所有的菜品口味
        List<DishFlavor> flavors = dishDto.getFlavors();

        // 遍历所有的口味  添加上菜品的id
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());


        // 保存菜品口味数据到菜品口味表
        dishFlavorService.saveBatch(flavors);

    }
}
