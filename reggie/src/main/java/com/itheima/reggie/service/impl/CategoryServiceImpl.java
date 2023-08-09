package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.common.CustomException;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.Setmeal;
import com.itheima.reggie.mapper.CategoryMapper;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.DishService;
import com.itheima.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    /**
     * 根据Id删除分类 删除之前需要进行判断
     * @param id
     */

    @Override
    public void remove(Long id){
        // 查询当前分类是否关联菜品 如果已经关联 抛出一个业务异常 终止删除分类
        // 需要根据category的id来查询有没有dish
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<Dish>();

        // 等职查询 查询所有dish字段中包含category_id字段的所有dish对象
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);// 匹配所有Id相等

        int count = dishService.count(dishLambdaQueryWrapper);

        if(count > 0){
            // 如果查询到了 说明已经关联到了菜品  抛出一个业务异常
            throw new CustomException("当前分类下关联了菜品 不可以删除");
        }

        // 查询当前分类是否关联了套餐  如果已经关联套餐 抛出一个异常
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper  = new LambdaQueryWrapper<>();
        // 添加查询条件 根据分类Id进行查询
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);

        int count2 = setmealService.count(setmealLambdaQueryWrapper);
        if(count2 > 0){
            // 已经关联套餐 抛出一个异常
            throw new CustomException("当前分类下面 关联了套餐 不可以删除");
        }

        // 正常删除分类
        super.removeById(id);
    }


}
