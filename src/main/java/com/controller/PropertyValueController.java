package com.controller;

import com.pojo.Propertyvalue;
import com.service.PropertyService;
import com.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PropertyValueController {
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    PropertyService propertyService;

    @RequestMapping("admin_propertyValue_edit")
    public String edit(Model model, int pid){
        propertyValueService.init(pid);
        List<Propertyvalue> pvs=propertyValueService.list(pid);
        model.addAttribute("pvs",pvs);
        return "admin/editPropertyValue";
    }

    @RequestMapping("admin_propertyValue_update")
    @ResponseBody
    public String update(Propertyvalue pv){
        propertyValueService.update(pv);
        return "success";
    }
}
