package com.service.impl;

import com.mapper.OrderMapper;
import com.pojo.Order;
import com.pojo.OrderExample;
import com.pojo.User;
import com.service.OrderService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    UserService userService;

    @Override
    public void add(Order order) {
        orderMapper.insert(order);
    }

    @Override
    public void delete(int id) {
        orderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Order order) {
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public Order get(int id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Order> list() {
        OrderExample example = new OrderExample();
        example.setOrderByClause("id desc");
        List<Order> orders = orderMapper.selectByExample(example);
        return orders;
    }

    public void setUser(Order order) {
        User user = userService.get(order.getUid());
        order.setUser(user);
    }

    public void setUser(List<Order> orders) {
        for (Order order : orders)
            setUser(order);
    }
}
