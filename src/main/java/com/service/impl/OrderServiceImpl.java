package com.service.impl;

import com.mapper.OrderMapper;
import com.pojo.Order;
import com.pojo.OrderExample;
import com.pojo.Orderitem;
import com.pojo.User;
import com.service.OrderItemService;
import com.service.OrderService;
import com.service.ProductService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    UserService userService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    ProductService productService;

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

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackForClassName = "Exception")
    public float add(Order order, List<Orderitem> ois) {
        float total=0;
        add(order);

//        测试
        if (false)
            throw new RuntimeException();

        for (Orderitem oi:ois){
            oi.setOid(order.getId());
            orderItemService.update(oi);
            total+=oi.getNumber()*productService.get(oi.getPid()).getPromotePrice();
        }

        return total;
    }

    @Override
    public List<Order> list(int uid) {
        OrderExample example=new OrderExample();
        example.createCriteria().andUidEqualTo(uid).andStatusNotEqualTo(OrderService.delete);
        List<Order> os=orderMapper.selectByExample(example);

        return os;
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
