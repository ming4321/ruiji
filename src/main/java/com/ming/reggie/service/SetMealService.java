package com.ming.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ming.reggie.common.R;
import com.ming.reggie.dto.SetmealDto;
import com.ming.reggie.entity.Setmeal;

import java.util.List;

public interface SetMealService extends IService<Setmeal> {

    public void saveWithDish(SetmealDto setmealDto);
    //删除套餐和与其关联的菜品数据
    public void removeWithDish(List<Long> ids);
}
