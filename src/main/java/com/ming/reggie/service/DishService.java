package com.ming.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ming.reggie.dto.DishDto;
import com.ming.reggie.entity.Dish;

public interface DishService extends IService<Dish> {
    public void saveWithFlavor(DishDto dishDto);

    //根据菜品id查询菜品信息和口味信息
    public DishDto getByIdWithFlavor(Long id);

    public void updateWithFlavor(DishDto dishDto);
}
