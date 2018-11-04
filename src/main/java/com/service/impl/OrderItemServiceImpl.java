package com.service.impl;

import com.mapper.OrderitemMapper;
import com.pojo.Order;
import com.pojo.Orderitem;
import com.pojo.OrderitemExample;
import com.pojo.Product;
import com.service.OrderItemService;
import com.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    OrderitemMapper orderitemMapper;
    @Autowired
    ProductService productService;

    @Override
    public void add(Orderitem orderitem) {
        orderitemMapper.insert(orderitem);
    }

    @Override
    public void delete(int id) {
        orderitemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Orderitem orderitem) {
        orderitemMapper.updateByPrimaryKeySelective(orderitem);
    }

    @Override
    public Orderitem get(int id) {
        Orderitem orderitem = orderitemMapper.selectByPrimaryKey(id);
        orderitem.setProduct(productService.get(orderitem.getPid()));
        return orderitem;
    }

    @Override
    public List<Orderitem> list() {
        OrderitemExample example = new OrderitemExample();
        example.setOrderByClause("id desc");
        List<Orderitem> orderitems = orderitemMapper.selectByExample(example);
        return orderitems;
    }

    @Override
    public void fill(Order order) {
        OrderitemExample example = new OrderitemExample();
        example.createCriteria().andOidEqualTo(order.getId());
        example.setOrderByClause("id desc");
        List<Orderitem> orderitems = orderitemMapper.selectByExample(example);

        float total = 0;
        int totalNumber = 0;
        for (Orderitem orderitem : orderitems) {
            Product product=productService.get(orderitem.getPid());
            productService.setFirstProductImage(product);
            orderitem.setProduct(product);
        }

        for (Orderitem orderitem : orderitems) {
//            orderitem.setProduct(productService.get(orderitem.getPid()));
            total += orderitem.getNumber() * orderitem.getProduct().getPromotePrice();
            totalNumber += orderitem.getNumber();
        }
        order.setTotal(total);
        order.setTotalNumber(totalNumber);
        order.setOrderItems(orderitems);
    }


    @Override
    public void fill(List<Order> orders) {
        for (Order order : orders) {
            fill(order);
        }
    }
}
