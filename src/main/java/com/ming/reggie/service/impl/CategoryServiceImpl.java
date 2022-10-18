package com.ming.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ming.reggie.common.CustomException;
import com.ming.reggie.entity.Category;
import com.ming.reggie.entity.Dish;
import com.ming.reggie.entity.Setmeal;
import com.ming.reggie.mapper.CategoryMapper;
import com.ming.reggie.service.CategoryService;
import com.ming.reggie.service.DishService;
import com.ming.reggie.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetMealService setMealService;
    /**
     * 根据id删除分类，删除之前需进行判断
     * @param ids
     */
    @Override
    public void remove(Long ids) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
      //查询当前分类是否关联了菜品，如是，抛出业务异常
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,ids);
        int count1 = dishService.count(dishLambdaQueryWrapper);
        if(count1 > 0 ){
            //抛出业务异常
            throw new CustomException("当前分类关联了菜品，不能删除");
        }
        //查询当前分类是否关联了套餐，如是，抛出业务异常
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,ids);
        int count2 = setMealService.count(setmealLambdaQueryWrapper);
        if(count2 > 0){
            //抛出业务异常
            throw new CustomException("当前分类关联了套餐，不能删除");
        }
        super.removeById(ids);

    }
}
