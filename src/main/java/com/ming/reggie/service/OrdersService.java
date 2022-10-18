package com.ming.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ming.reggie.entity.Orders;

public interface OrdersService extends IService<Orders> {

    public void submit(Orders orders);
}
