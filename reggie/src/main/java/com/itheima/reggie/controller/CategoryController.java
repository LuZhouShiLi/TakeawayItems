package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.service.CategoryService;
import com.ustc.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;// 注入业务层接口

    /**
     * 新增分类
     * 页面发送ajax请求 将新增分类窗口输入以json形式提交到服务端
     * 服务端Controller接受页面提交的数据并且调用Service将数据进行保存
     * Service调用Mapper操作数据库  保存数据
     * 新增菜品分类和新增套餐分类请求的服务端地址和提交的json数据机构相同  所以服务端只需要提供一个方法统一处理即可
     */
    @PostMapping
    public R<String> save(@RequestBody Category category){

        categoryService.save(category);
        log.info("category:{}",category);// 输出日志
        return R.success("新增分类成功");// 返回执行结果
    }

    /**
     * 分页查询
     *
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize){
        // 分页构造器
        Page<Category> pageInfo = new Page<>(page,pageSize);

        // 条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort);

        // 进行分页查询
        categoryService.page(pageInfo,queryWrapper);

        return  R.success(pageInfo);
    }

    /**
     * 根据Id删除分类
     * @param id
     * @return
     */
    @DeleteMapping
    public R<String> delete( Long id){
        log.info("删除分类 id:{}",id);
        categoryService.removeById(id);// 页面传回来id
        return R.success("分类信息删除成功");
    }

    /**
     * 根据id修改分类信息
     * @param category
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Category category){
        log.info("修改分类信息:{}",category);
        categoryService.updateById(category);
        return R.success("修改分类信息成功");
    }

}