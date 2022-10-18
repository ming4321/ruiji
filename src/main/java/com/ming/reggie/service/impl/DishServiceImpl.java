package com.ming.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ming.reggie.dto.DishDto;
import com.ming.reggie.entity.Dish;
import com.ming.reggie.entity.DishFlavor;
import com.ming.reggie.mapper.DishMapper;
import com.ming.reggie.service.DishFlavorService;
import com.ming.reggie.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    private DishFlavorService dishFlavorService;
    @Transactional
    public void saveWithFlavor(DishDto dishDto){
        //保存菜品的基本信息到dish表
        this.save(dishDto);

        Long dishId = dishDto.getId();
       List<DishFlavor> list =  dishDto.getFlavors();
       for(DishFlavor flover : list){
           flover.setDishId(dishId);
       }
        //保存菜品口味数据到菜品口味表
        dishFlavorService.saveBatch(dishDto.getFlavors());
    }

    /**
     *  根据菜品id查询菜品信息和口味信息
     * @param id
     * @return
     */
    @Override
    public DishDto getByIdWithFlavor(Long id) {
        //险查询菜品信息,从dish表查
        Dish dish = this.getById(id);

        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish,dishDto);
        //查询口味信息，从dish_flavor查询
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dish.getId());
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(flavors);
        return dishDto;
    }

    @Override
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {
        //更新dish表基本信息
        this.updateById(dishDto);
        //清理当前菜品对应口味数据 dish-flavor的delete操作
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dishDto.getId());
        dishFlavorService.remove(queryWrapper);
        //清理当前菜品对应口味数据 dish-flavor的insert操作
        List<DishFlavor> flavors = dishDto.getFlavors();
        for(DishFlavor item : flavors){
            item.setDishId(dishDto.getId());
        }
        dishFlavorService.saveBatch(flavors);
    }
}
