package com.service;

import com.pojo.Order;
import com.pojo.Orderitem;

import java.util.List;

public interface OrderService {

    String waitPay = "waitPay";
    String waitDelivery = "waitDelivery";
    String waitConfirm = "waitConfirm";
    String waitReview = "waitReview";
    String finish = "finish";
    String delete = "delete";

    void add(Order order);

    void delete(int id);

    void update(Order order);

    Order get(int id);

    List<Order> list();

    float add(Order order, List<Orderitem> ois);

    List<Order> list(int uid);
}
