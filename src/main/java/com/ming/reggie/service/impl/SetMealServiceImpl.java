package com.ming.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ming.reggie.common.CustomException;
import com.ming.reggie.common.R;
import com.ming.reggie.dto.SetmealDto;
import com.ming.reggie.entity.Setmeal;
import com.ming.reggie.entity.SetmealDish;
import com.ming.reggie.mapper.SetMealMapper;
import com.ming.reggie.service.SetMealDishService;
import com.ming.reggie.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SetMealServiceImpl extends ServiceImpl<SetMealMapper, Setmeal> implements SetMealService {
      @Autowired
    private  SetMealDishService setMealDishService;
    /**
     * 新增套餐，同时保存套餐和菜品的关联关系
     * @param setmealDto
     * @return
     */
    @Override
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        this.save(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        for(SetmealDish item : setmealDishes){
            item.setSetmealId(setmealDto.getId());
        }
        setMealDishService.saveBatch(setmealDishes);


    }

    @Override
    @Transactional
    public void removeWithDish(List<Long> ids) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId,ids);
        queryWrapper.eq(Setmeal::getStatus,1);
        int count = this.count(queryWrapper);
        if(count > 0){
            throw new CustomException("套餐在售，无法删除");
        }
        this.removeByIds(ids);
        //删除关系表中的数据
        LambdaQueryWrapper<SetmealDish> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.in(SetmealDish::getSetmealId,ids);
       setMealDishService.remove(queryWrapper2);
    }
}
