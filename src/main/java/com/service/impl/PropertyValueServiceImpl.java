package com.service.impl;

import com.mapper.PropertyvalueMapper;
import com.pojo.Property;
import com.pojo.Propertyvalue;
import com.pojo.PropertyvalueExample;
import com.service.ProductService;
import com.service.PropertyService;
import com.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyValueServiceImpl implements PropertyValueService {
    @Autowired
    PropertyvalueMapper mapper;
    @Autowired
    PropertyService propertyService;
    @Autowired
    ProductService productService;

    @Override
    public List<Propertyvalue> init(int pid) {
        List<Property> pts = propertyService.list(productService.get(pid).getCid());
        for (Property pt : pts) {
            Propertyvalue pv = get(pid, pt.getId());
            if (pv == null) {
                pv = new Propertyvalue();
                pv.setProperty(pt);
                pv.setPid(pid);
                pv.setPtid(pt.getId());
                mapper.insert(pv);
            }
        }
        return null;
    }

    @Override
    public List<Propertyvalue> list(int pid) {
        PropertyvalueExample example=new PropertyvalueExample();
        example.createCriteria().andPidEqualTo(pid);
        List<Propertyvalue> pvs=mapper.selectByExample(example);
        return pvs;
    }

    @Override
    public void update(Propertyvalue pv) {
        
    }

    @Override
    public Propertyvalue get(int pid, int ptid) {
        PropertyvalueExample example = new PropertyvalueExample();
        example.createCriteria().andPidEqualTo(pid).andPtidEqualTo(ptid);
        List<Propertyvalue> pvs = mapper.selectByExample(example);
        if (pvs == null)
            return null;
        return pvs.get(0);
    }
}
