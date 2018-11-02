package com.controller;

import com.pojo.Product;
import com.pojo.Property;
import com.pojo.Propertyvalue;
import com.service.PropertyService;
import com.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class PropertyValueController {
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    PropertyService propertyService;

    @RequestMapping("admin_propertyValue_edit")
    public String edit(Model model, int pid){
        List<Propertyvalue> pvs=propertyValueService.init(pid);
        for (Propertyvalue pv:pvs){
            Property pt=propertyService.get(pv.getPtid());
        }

        model.addAttribute("pvs",pvs);
        return "admin/editPropertyValue";
    }

    @RequestMapping("admin_propertyValue_update")
    public String update(Propertyvalue pv){
        propertyValueService.update(pv);
        return "success";
    }
}
