package com.controller;

import com.pojo.Product;
import com.pojo.Productimage;
import com.service.ProductImageService;
import com.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class ProductImageController {
    @Autowired
    ProductImageService productImageService;
    @Autowired
    ProductService productService;

    @RequestMapping("admin_productImage_list")
    public String list(Model model,int pid){
        Product product=productService.get(pid);
        List<Productimage> piSingle=productImageService.list(pid,ProductImageService.type_single);
        List<Productimage> piDetail=productImageService.list(pid,ProductImageService.type_detail);

        model.addAttribute("piSingle",piSingle);
        model.addAttribute("piDetail",piDetail);
        model.addAttribute("product",product);
        return "admin/listProductImage";
    }

    @RequestMapping("admin_productImage_add")
    public String add(Productimage productimage, MultipartFile image, HttpSession session) throws IOException {
        System.out.println(session.getServletContext().getContextPath());
        productImageService.add(productimage);
        String name=productimage.getId()+".jpg";
        String path;
        if (productimage.getType().equals(ProductImageService.type_single))
            path=session.getServletContext().getRealPath("img/productImageSingle");
        else
            path=session.getServletContext().getRealPath("img/productImageDetail");
        File file=new File(path,name);
        file.mkdirs();
        image.transferTo(file);

        return "redirect:admin_productImage_list?pid="+productimage.getPid();
    }

    @RequestMapping("admin_productImage_delete")
    public String delete(int id,HttpSession session){
        Productimage productimage=productImageService.get(id);
        productImageService.delete(id);
        String name=productimage.getId()+".jpg";
        String path;
        if (productimage.getType().equals(ProductImageService.type_single))
            path=session.getServletContext().getRealPath("img/productImageSingle");
        else
            path=session.getServletContext().getRealPath("img/productImageDetail");
        File file=new File(path,name);
        file.delete();

        return "redirect:admin_productImage_list?pid="+productimage.getPid();
    }
}
