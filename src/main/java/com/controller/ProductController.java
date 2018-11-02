package com.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pojo.Category;
import com.pojo.Product;
import com.service.CategoryService;
import com.service.ProductService;
import com.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @RequestMapping("admin_product_list")
    public String list(Model model, Page page,int cid){
        Category category=categoryService.get(cid);

        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Product> list=productService.list(cid);

        int total= (int) new PageInfo<>(list).getTotal();
        page.setTotal(total);

        model.addAttribute("list",list);
        model.addAttribute("category",category);
        model.addAttribute("page",page);

        return "admin/listProduct";
    }

    @RequestMapping("admin_product_add")
    public String add(Product product){
        product.setCreateDate(new Date());
        productService.add(product);
        return "redirect:admin_product_list?cid="+ product.getCid();
    }

    @RequestMapping("admin_product_delete")
    public String delete(int id){
        Product product=productService.get(id);
        productService.delete(id);
        return "redirect:admin_product_list?cid="+ product.getCid();
    }

    @RequestMapping("admin_product_edit")
    public String edit(Model model,int id){
        Product product=productService.get(id);

        model.addAttribute("product",product);
        return "admin/editProduct";
    }

    @RequestMapping("admin_product_update")
    public String update(Product product){
        productService.update(product);
        return "redirect:admin_product_list?cid="+ product.getCid();
    }
}
