package com.ming.reggie.dto;

import com.ming.reggie.entity.Setmeal;
import com.ming.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
