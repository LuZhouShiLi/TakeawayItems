package com.itheima.reggie.dto;

import com.itheima.reggie.entity.Setmeal;
import com.itheima.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;


/**
 * 套餐页面数据传输对象  继承套餐类 然后扩展两个属性
 */
@Data
public class SetmealDto extends Setmeal {

    // 表示一个套餐的所有菜品
    private List<SetmealDish> setmealDishes;// 菜品

    private String categoryName;
}
