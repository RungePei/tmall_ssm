package com.service;

import com.pojo.Order;
import com.pojo.Orderitem;

import java.util.List;

public interface OrderItemService {
    void add(Orderitem orderitem);

    void delete(int id);

    void update(Orderitem orderitem);

    Orderitem get(int id);

    List<Orderitem> list();

    void fill(Order order);

    void fill(List<Order> orders);
}
