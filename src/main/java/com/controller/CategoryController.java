package com.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pojo.Category;
import com.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.util.Page;
import com.util.UploadedImageFile;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @RequestMapping("admin_category_list")
    public String list(Model model, Page page){
//        List<Category> cs= categoryService.list(page);
//        int total=categoryService.getTotal();
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Category> cs= categoryService.list();
        int total = (int) new PageInfo<>(cs).getTotal();
        page.setTotal(total);
        model.addAttribute("page",page);
        model.addAttribute("cs",cs);
        return "admin/listCategory";
    }

    @RequestMapping("admin_category_add")
    public String add(Category category, UploadedImageFile uploadedImageFile, HttpSession session) throws IOException {
        categoryService.add(category);
        File file=new File(session.getServletContext().getRealPath("/img/category"),
                category.getId()+".jpg");
        System.out.println(file.getAbsolutePath());
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        uploadedImageFile.getImage().transferTo(file);
        return "redirect:admin_category_list";
    }

    @RequestMapping("admin_category_delete")
    public String delete(int id ,HttpSession session){
        File file=new File(session.getServletContext().getRealPath("img/category"),id+".jpg");
        file.delete();
        categoryService.delete(id);
        return "redirect:admin_category_list";
    }

    @RequestMapping("admin_category_update")
    public String update(Category category , HttpSession session, MultipartFile image) throws IOException {
        categoryService.update(category);
        if (image!=null&!image.isEmpty()){
            File file=new File(session.getServletContext().getRealPath("img/category"), category.getId()+".jpg");
            image.transferTo(file);
        }
        return "redirect:admin_category_list";
    }

}
