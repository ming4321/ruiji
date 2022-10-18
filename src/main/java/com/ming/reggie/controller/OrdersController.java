package com.ming.reggie.controller;

import com.ming.reggie.common.R;
import com.ming.reggie.entity.Orders;
import com.ming.reggie.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrdersController {
     @Autowired
     private OrdersService ordersService;
     @PostMapping("/submit")
     public R<String> submit(@RequestBody Orders orders){
         log.info("order: {}",orders);
         ordersService.submit(orders);
         return R.success("订单支付成功");
     }
}

