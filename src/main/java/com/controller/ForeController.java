package com.controller;

import com.pojo.*;
import com.service.*;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ForeController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    OrderService orderService;

    @RequestMapping("foreHome")
    public String home(Model model) {
        List<Category> categories = categoryService.list();
        productService.fill(categories);
        productService.fillByRow(categories);

        model.addAttribute("cs", categories);
        return "fore/home";
    }

    @RequestMapping("foreRegister")
    public String register(Model model, User user) {
        String name = user.getName();
        name = HtmlUtils.htmlEscape(name);
        user.setName(name);
        if (userService.isExits(name)) {
            String msg = "用户名已存在，请换个";
            model.addAttribute("msg", msg);
            model.addAttribute("user", null);
            return "fore/register";
        }
        userService.add(user);
        return "redirect:registerSuccessPage";
    }

    @RequestMapping("foreLogin")
    public String login(User user, Model model, HttpSession session) {
        user.setName(HtmlUtils.htmlEscape(user.getName()));
        User u = userService.get(user);

        if (u == null) {
            String msg = "用户名或密码错误";
            model.addAttribute("msg", msg);
            return "fore/login";
        }
        session.setAttribute("user", u);
        return "redirect:foreHome";
    }

    @RequestMapping("foreLogout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:foreHome";
    }

    @RequestMapping("foreProduct")
    public String product(Model model, int pid) {
        Product product = productService.get(pid);
        List<Productimage> productSingleImages = productImageService.list(pid, ProductImageService.type_single);
        List<Productimage> productDetailImages = productImageService.list(pid, ProductImageService.type_detail);
        List<Review> reviews = reviewService.list(pid);
        List<Propertyvalue> pvs = propertyValueService.list(pid);

        productService.setFirstProductImage(product);
        productService.setSaleAndReviewNumber(product);
        product.setProductSingleImages(productSingleImages);
        product.setProductDetailImages(productDetailImages);
        product.setCategory(categoryService.get(product.getCid()));

        model.addAttribute("p", product);
        model.addAttribute("reviews", reviews);
        model.addAttribute("pvs", pvs);

        return "fore/product";
    }

    @RequestMapping("forecheckLogin")
    @ResponseBody
    public String checkLogin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null)
            return "fail";
        return "success";
    }

    @RequestMapping("foreloginAjax")
    @ResponseBody
    public String loginAjax(User user, HttpSession session) {
        user.setName(HtmlUtils.htmlEscape(user.getName()));
        User u = userService.get(user);

        if (u == null)
            return "fail";
        session.setAttribute("user", u);
        return "success";
    }

    @RequestMapping("foreSearch")
    public String search(String keyword, Model model) {
        List<Product> ps = productService.search(keyword);
        productService.setSaleAndReviewNumber(ps);

        model.addAttribute("ps", ps);
        return "fore/searchResult";
    }

    @RequestMapping("forebuyone")
    public String buyone(int pid, int num, HttpSession session) {
        Product product = productService.get(pid);
        User user = (User) session.getAttribute("user");
        boolean hasFound = false;
        int oiid = 0;

        List<Orderitem> orderitems = orderItemService.listByUser(user.getId());
        for (Orderitem oi : orderitems) {
            if (oi.getProduct().getId().intValue() == pid) {
                oi.setNumber(oi.getNumber() + num);
                orderItemService.update(oi);
                hasFound = true;
                oiid = oi.getId();
                break;
            }
        }
        if (!hasFound) {
            Orderitem oi = new Orderitem();
            oi.setNumber(num);
            oi.setUid(user.getId());
            oi.setPid(pid);
            oi.setProduct(product);
            orderItemService.add(oi);
            oiid = oi.getId();
        }

        return "redirect:forebuy?oiid=" + oiid;
    }

    @RequestMapping("forebuy")
    public String buy(String[] oiid, Model model, HttpSession session) {
        List<Orderitem> ois = new ArrayList<>();
        float total = 0;

        for (String stringId : oiid) {
            int id = Integer.parseInt(stringId);
            Orderitem oi = orderItemService.get(id);
            total += oi.getNumber() * productService.get(oi.getPid()).getPromotePrice();
            ois.add(oi);
        }
        model.addAttribute("total", total);
        session.setAttribute("ois", ois);

        return "fore/buy";
    }

    @RequestMapping("foreaddCart")
    @ResponseBody
    public String addCart(int pid, int num, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Orderitem> ois = orderItemService.listByUser(user.getId());
        boolean hasFound = false;

        for (Orderitem oi : ois) {
            if (oi.getPid().intValue() == pid) {
                oi.setNumber(oi.getNumber() + num);
                orderItemService.update(oi);
                hasFound = true;
            }
        }

        if (!hasFound) {
            Orderitem oi = new Orderitem();
            oi.setNumber(num);
            oi.setUid(user.getId());
            oi.setPid(pid);
            orderItemService.add(oi);
        }

        return "success";
    }

    @RequestMapping("forecart")
    public String cart(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        List<Orderitem> ois = orderItemService.listByUser(user.getId());

        model.addAttribute("ois", ois);
        return "fore/cart";
    }

    @RequestMapping("forechangeOrderItem")
    @ResponseBody
    public String changeOrderItem(HttpSession session, int pid, int number) {
        User user = (User) session.getAttribute("user");
        if (user == null)
            return "fail";

        Orderitem oi = orderItemService.get(pid, user.getId());
        oi.setNumber(number);
        orderItemService.update(oi);

        return "success";
    }

    @RequestMapping("foredeleteOrderItem")
    @ResponseBody
    public String deleteOrderItem(int oiid, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null)
            return "fail";

        orderItemService.delete(oiid);
        return "success";
    }

    @RequestMapping("forecreateOrder")
    public String createOrder(Order order, HttpSession session) {
        User user = (User) session.getAttribute("user");
        String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + RandomUtils.nextInt(10000);
        order.setOrderCode(orderCode);
        order.setCreateDate(new Date());
        order.setUid(user.getId());
        order.setStatus(OrderService.waitPay);
        List<Orderitem> ois = (List<Orderitem>) session.getAttribute("ois");

        float total = orderService.add(order, ois);
        return "redirect:forealipay?oid=" + order.getId() + "&total=" + total;
    }

    @RequestMapping("forepayed")
    public String payed(Model model, int oid) {
        Order order = orderService.get(oid);
        order.setStatus(OrderService.waitDelivery);
        order.setPayDate(new Date());
        orderService.update(order);

        model.addAttribute("order", order);

        return "fore/payed";
    }

    @RequestMapping("forebought")
    public String bought(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Order> orders = orderService.list(user.getId());
        orderItemService.fill(orders);

        model.addAttribute("os", orders);
        return "fore/bought";
    }

    @RequestMapping("foreconfirmPay")
    public String confirmPay(Model model, int oid) {
        Order order = orderService.get(oid);
        orderItemService.fill(order);

        model.addAttribute("o", order);

        return "fore/confirm";
    }

    @RequestMapping("foreorderConfirmed")
    public String orderConfirmed(Model model, int oid) {
        Order order = orderService.get(oid);
        order.setStatus(OrderService.waitReview);
        order.setConfirmDate(new Date());

        orderService.update(order);
        return "fore/orderConfirmed";
    }

    @RequestMapping("foredeleteOrder")
    @ResponseBody
    public String deleteOrder(int oid) {
        Order order = orderService.get(oid);
        order.setStatus(OrderService.delete);
        orderService.update(order);
        return "success";
    }

    @RequestMapping("forereview")
    public String review(int oid, Model model) {
        Order order = orderService.get(oid);
        orderItemService.fill(order);
        Product product = order.getOrderItems().get(0).getProduct();
        List<Review> reviews = reviewService.list(product.getId());

        model.addAttribute("p", product);
        model.addAttribute("o", order);
        model.addAttribute("reviews", reviews);

        return "fore/review";
    }

    @RequestMapping("foredoreview")
    public String doreview(Model model, HttpSession session, @RequestParam("oid") int oid, @RequestParam("pid") int pid, String content) {
        Order o = orderService.get(oid);
        o.setStatus(OrderService.finish);
        orderService.update(o);

        content = HtmlUtils.htmlEscape(content);

        User user = (User) session.getAttribute("user");
        Review review = new Review();
        review.setContent(content);
        review.setPid(pid);
        review.setCreateDate(new Date());
        review.setUid(user.getId());
        reviewService.add(review);

        return "redirect:forereview?oid=" + oid + "&showonly=true";
    }

}
