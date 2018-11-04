package com.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pojo.Order;
import com.service.OrderItemService;
import com.service.OrderService;
import com.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;

    @RequestMapping("admin_order_list")
    public String list(Model model, Page page){
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Order> orders=orderService.list();

        int total= (int) new PageInfo<>(orders).getTotal();
        page.setTotal(total);

        orderItemService.fill(orders);

        model.addAttribute("orders",orders);
        model.addAttribute("page",page);

        return "admin/listOrder";
    }

    @RequestMapping("admin_order_delivery")
    public String delivery(Order order){
        order.setDeliveryDate(new Date());
        order.setStatus(OrderService.waitConfirm);
        orderService.update(order);
        return "redirect:admin_order_list";
    }
}
